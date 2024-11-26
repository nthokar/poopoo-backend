package ru.raperan.poopoo.keycloaksecurity;

import java.util.UUID;

public interface AuthorityService {

    /**
     * Проверяет имеет ли текущий пользователь права быть овнером...
     * Ну типо ownerId может быть id другого полльзователя или музея, и мы должны проверить
     * является ли текущий пользователь овнером или состоит ли если ownerId - id музея,
     * то состоит ли он в этом музее.
     * <p>
     * Когда происходит регестрация музея(регестрация пользователя с ролью MUSEUM)
     * создается и группа с таким же id как id музея. Может стоит делать не на группах... тебе решать
     */
    boolean hasAuthority(UUID ownerId);

    /**
     * Проверяет является текущий пользователь овнером
     */
    boolean hasPrimaryAuthority(UUID userId);
}
