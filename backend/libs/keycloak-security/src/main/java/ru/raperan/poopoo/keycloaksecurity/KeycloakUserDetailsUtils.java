package ru.raperan.poopoo.keycloaksecurity;

import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class KeycloakUserDetailsUtils {

    public static KeycloakPrincipal getPrincipal() {
       return (KeycloakPrincipal) SecurityContextHolder
                                      .getContext()
                                      .getAuthentication()
                                      .getPrincipal();
    }

    public static KeycloakSecurityContext getSecurityContext() {
        return getPrincipal().getKeycloakSecurityContext();
    }

    public static AccessToken getUserDetails() {
        return getSecurityContext().getToken();
    }
}
