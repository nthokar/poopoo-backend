package ru.raperan.poopoo.mainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.mainservice.domain.CollectionPlayState;
import ru.raperan.poopoo.mainservice.domain.TrackCollection;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectionPlayStateRepository extends JpaRepository<CollectionPlayState, UUID> {

    //als
    @Query(nativeQuery = true, value = "delete from collection_play_state cps where cps.collection_id = :collectionId and cps.owner_id = :ownerId")
    void deleteByCollectionAndOwnerId(@Param("collectionId") UUID collectionId, @Param("ownerId") UUID ownerId);

    @Query(nativeQuery = true, value = "delete from collection_play_state cps where cps.owner_id = :ownerId")
    void deleteByOwnerId(@Param("ownerId") UUID ownerId);

    Optional<CollectionPlayState> findByCollectionAndOwnerId(TrackCollection trackCollection, UUID ownerId);

    Optional<CollectionPlayState> findByOwnerId(UUID ownerId);
}
