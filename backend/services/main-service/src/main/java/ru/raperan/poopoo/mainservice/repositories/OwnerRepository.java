package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.Owner;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, UUID> {

    Optional<Owner> findByEmail(String email);
}
