package ru.raperan.poopoo.keycloaksecurity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@Configuration
@PropertySource(value = "classpath:application-keycloak.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "keycloak.properties")
public class KeycloakProperties {

    @NonNull
    private String serverUrl;

    @NonNull
    private String realm;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String clientId;

    @NonNull
    private String clientSecret;

    @Nullable
    private Jwt jwt;

    @Data
    public static class Jwt {

        @Nullable
        private final String preferredName;

    }

}
