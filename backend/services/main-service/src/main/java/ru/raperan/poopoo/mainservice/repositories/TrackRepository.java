package ru.raperan.poopoo.mainservice.repositories;

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
public interface TrackRepository extends JpaRepository<Track, UUID> {

    List<Track> findByAlbum(Album album);

    @Query(value = "select t.name from Track t")
    List<String> findAllNames();

    @Query(value = "select t from Track t where LOWER(t.name) = LOWER(:trackName)")
    List<Track> findAllByName(@Param("trackName") String trackName);


}
