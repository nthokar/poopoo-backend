package ru.raperan.poopoo.poopoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.poopoo.domain.MusicCollection;

import java.util.UUID;

@Repository
public interface MusicCollectionRepository extends JpaRepository<MusicCollection, UUID> {}
