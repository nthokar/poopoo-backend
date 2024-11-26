package ru.raperan.poopoo.keycloaksecurity;

import lombok.Getter;

@Getter
public enum Roles {

    MODERATOR(KeycloakConstants.ROLE_PREFIX + "MODERATOR"),
    ADMIN(KeycloakConstants.ROLE_PREFIX + "ADMIN"),
    MUSEUM(KeycloakConstants.ROLE_PREFIX + "MUSEUM");

    final String name;

    Roles(String name){
        this.name = name;
    }
}