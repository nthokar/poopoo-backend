package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.raperan.poopoo.mainservice.domain.Author;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {


}
