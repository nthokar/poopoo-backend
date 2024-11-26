package ru.raperan.poopoo.keycloaksecurity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeycloakConstants {
    public static final String REALM_ACCESS = "realm_access";
    public static final String ROLES = "roles";
    public static final String ROLE_PREFIX = "ROLE_";
}
