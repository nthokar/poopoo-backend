package ru.raperan.poopoo.mainservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.Owner;
import ru.raperan.poopoo.mainservice.repositories.OwnerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final OwnerRepository ownerRepository;

    public Owner getCurrentOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) ((Jwt) ((JwtAuthenticationToken) authentication).getCredentials()).getClaims().get("email");

        Optional<Owner> owner = ownerRepository.findByEmail(email);
        if (owner.isEmpty()) {
            Owner newOwner = new Owner().setEmail(email);
            ownerRepository.save(newOwner);
            return newOwner;
        }
        return owner.get();


    }
}
