package de.muenchen.oss.digiwf.akdb.integration;


import de.muenchen.oss.digiwf.akdb.integration.core.application.CalcNumberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@RequiredArgsConstructor
@ComponentScan(basePackages = {"de.muenchen.oss.digiwf.akdb.integration.core"})
public class AkdbIntegrationAutoconfiguration {

    @Bean
    public CalcNumberUseCase calcNumberUseCase() {
        return new CalcNumberUseCase();
    }

}
