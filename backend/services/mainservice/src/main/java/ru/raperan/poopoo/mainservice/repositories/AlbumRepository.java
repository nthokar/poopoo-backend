package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.Album;

import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {}
