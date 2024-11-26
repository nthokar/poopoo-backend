package ru.raperan.poopoo.keycloaksecurity;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Override
    public boolean hasAuthority(UUID ownerId) {
        return false;
    }

    @Override
    public boolean hasPrimaryAuthority(UUID ownerId) {
        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        UUID curUserid = UUID.fromString(SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName());
        return ownerId.equals(curUserid);
    }
}
