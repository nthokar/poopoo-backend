package ru.raperan.poopoo.mainservice.repositories;

import org.checkerframework.checker.units.qual.A;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.Album;
import ru.raperan.poopoo.mainservice.domain.Track;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    @Query(value = "select a.name from Album a")
    List<String> findAllNames();

    @Query(value = "select a from Album a where LOWER(a.name) = LOWER(:albumName)")
    List<Album> findAllByName(@Param("albumName") String albumName);
}
