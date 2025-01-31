package de.muenchen.oss.digiwf.spring.security.userinfo;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import de.muenchen.oss.digiwf.spring.security.JwtClaims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service, der einen OIDC /userinfo Endpoint aufruft (mit JWT Bearer Auth) und dort die enthaltenen
 * "authorities" und "user_roles" extrahiert.
 */
@Slf4j
public class UserInfoAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final String NAME_AUTHENTICATION_CACHE = "authentication_cache";
  private static final int AUTHENTICATION_CACHE_ENTRY_SECONDS_TO_EXPIRE = 60;

  private final String userInfoUri;
  private final RestTemplate restTemplate;
  private final Cache cache;

  /**
   * Erzeugt eine neue Instanz.
   *
   * @param userInfoUri         userinfo Endpoint URI
   * @param restTemplateBuilder ein {@link RestTemplateBuilder}
   */
  public UserInfoAuthoritiesConverter(final String userInfoUri, final RestTemplateBuilder restTemplateBuilder) {
    this.userInfoUri = userInfoUri;
    this.restTemplate = restTemplateBuilder.build();
    this.cache = new CaffeineCache(NAME_AUTHENTICATION_CACHE,
        Caffeine.newBuilder()
            .expireAfterWrite(AUTHENTICATION_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
            .ticker(Ticker.systemTicker())
            .build());
  }

  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
    return loadAuthorities(source);
  }


  /**
   * Ruft den /userinfo Endpoint und extrahiert {@link GrantedAuthority}s aus dem "authorities"
   * Claim und roles aus dem "user_roles" claim.
   *
   * @param jwt der JWT
   * @return die {@link GrantedAuthority}s gem. Claim "authorities" des /userinfo Endpoints
   */
  public Collection<GrantedAuthority> loadAuthorities(final Jwt jwt) {
    final ValueWrapper valueWrapper = this.cache.get(jwt.getSubject());
    if (valueWrapper != null) {
      // value present in cache
      @SuppressWarnings("unchecked") final Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) valueWrapper.get();
      log.debug("Resolved authorities (from cache): {}", authorities);
      return authorities;
    }

    log.debug("Fetching user-info for token subject: {}", jwt.getSubject());
    final HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
    final HttpEntity<String> entity = new HttpEntity<>(headers);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    try {
      @SuppressWarnings("unchecked")
      final Map<String, Object> map = this.restTemplate.exchange(this.userInfoUri, HttpMethod.GET, entity, Map.class).getBody();
      log.debug("Response from user-info Endpoint: {}", map);
      if (map != null && map.containsKey(JwtClaims.AUTHORITIES)) {
        authorities.addAll(asAuthorities(map.get(JwtClaims.AUTHORITIES)));
      }
      if (map != null && map.containsKey(JwtClaims.ROLES)) {
        authorities.addAll(asAuthorities(map.get(JwtClaims.ROLES)));
      }
      log.debug("Resolved Authorities (from /userinfo Endpoint): {}", authorities);
      // store
      this.cache.put(jwt.getSubject(), authorities);
    } catch (final Exception e) {
      log.error(String.format("Could not fetch user details from %s - user is granted NO authorities", this.userInfoUri), e);
    }
    return authorities;
  }

  private static List<GrantedAuthority> asAuthorities(Object object) {
    final List<GrantedAuthority> authorities = new ArrayList<>();
    if (object instanceof Collection) {
      final Collection<?> collection = (Collection<?>) object;
      object = collection.toArray(new Object[0]);
    }
    if (ObjectUtils.isArray(object)) {
      authorities.addAll(
          Stream.of(((Object[]) object))
              .map(Object::toString)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList())
      );
    }
    return authorities;
  }

}

