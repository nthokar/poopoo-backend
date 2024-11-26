package ru.raperan.poopoo.keycloaksecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final KeycloakProperties keycloakProperties;

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
        return Stream.concat(jwtGrantedAuthoritiesConverter.convert(source).stream(),
            extractResourceRoles(source)).collect(Collectors.toSet());
    }

    private Stream<GrantedAuthority> extractResourceRoles(Jwt source) {
        Map<String, Object> realmAccess = source.getClaim(KeycloakConstants.REALM_ACCESS) ;

        if (realmAccess != null) {
            Collection<String> realmRoles = source.getClaimAsStringList(KeycloakConstants.ROLES);
            if(realmRoles != null) {
                return realmRoles.stream()
                                 .map(role -> new SimpleGrantedAuthority(KeycloakConstants.ROLE_PREFIX + role));
            }
        }
        return Stream.of();
    }

    private String getPrincipledName(Jwt source) {
        String claimName = JwtClaimNames.SUB;
        if (Objects.requireNonNull(keycloakProperties.getJwt()).getPreferredName() != null
            && source.hasClaim(claimName) ) {
            claimName = keycloakProperties.getJwt().getPreferredName();
        }
        return source.getClaim(claimName);
    }

}
