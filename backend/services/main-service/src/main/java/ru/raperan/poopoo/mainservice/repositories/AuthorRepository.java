package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.Album;
import ru.raperan.poopoo.mainservice.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query(value = "select a.name from Author a")
    List<String> findAllNames();

    @Query(value = "select a from Author a where LOWER(a.name) = LOWER(:authorName)")
    List<Author> findAllByName(@Param("authorName") String authorName);

}
