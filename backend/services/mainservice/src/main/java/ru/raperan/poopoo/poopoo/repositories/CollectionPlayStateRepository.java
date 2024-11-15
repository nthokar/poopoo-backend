package ru.raperan.poopoo.poopoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.poopoo.domain.CollectionPlayState;
import ru.raperan.poopoo.poopoo.domain.MusicCollection;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollectionPlayStateRepository extends JpaRepository<CollectionPlayState, UUID> {

    //als
    @Query(nativeQuery = true, value = "delete from collection_play_state cps where cps.collection_id = :collectionId")
    void deleteByCollection(@Param("collectionId") UUID collectionId);

    Optional<CollectionPlayState> findByCollection(MusicCollection musicCollection);
}
