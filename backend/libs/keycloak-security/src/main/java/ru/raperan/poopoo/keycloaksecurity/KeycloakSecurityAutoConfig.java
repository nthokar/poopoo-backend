package ru.raperan.poopoo.keycloaksecurity;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

//TODO Убрать permitAll
@EnableWebSecurity
@Configuration
@EnableConfigurationProperties(value = {
    KeycloakProperties.class
})
public class KeycloakSecurityAutoConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   KeycloakProperties keycloakProperties)
        throws Exception {
        http.oauth2ResourceServer(oauth2 ->
            oauth2
                .jwt(jwtConfigurer ->
                    jwtConfigurer
                        .jwtAuthenticationConverter(
                            jwtAuthenticationConverter(keycloakProperties)))
        );
        return http
            .csrf()
            .disable()
            .authorizeHttpRequests(auth ->
                auth
                    .anyRequest()
                    .permitAll())
            .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(
        KeycloakProperties keycloakProperties) {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtAuthConverter(keycloakProperties));
        return converter;
    }

    @Bean
    public KeycloakUserDetailsUtils keycloakUserDetailsService() {
        return new KeycloakUserDetailsUtils();
    }

    @Bean
    public AuthorityService authorityService() {
        return new AuthorityServiceImpl();
    }

}
