package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.TrackCollection;

import java.util.UUID;

@Repository
public interface TrackCollectionRepository extends JpaRepository<TrackCollection, UUID> {}
