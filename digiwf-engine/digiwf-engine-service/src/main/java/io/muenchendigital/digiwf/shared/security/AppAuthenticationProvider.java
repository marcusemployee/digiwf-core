/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.shared.security;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get current camunda authentication informaiton.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class AppAuthenticationProvider {

    private final IdentityService identityService;

    public List<String> getCurrentUserGroups() {
        return this.identityService.getCurrentAuthentication().getGroupIds();
    }

    public String getCurrentUserId() {
        return this.identityService.getCurrentAuthentication().getUserId();
    }
}