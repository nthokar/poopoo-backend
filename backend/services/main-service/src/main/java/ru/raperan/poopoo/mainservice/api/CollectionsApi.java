package ru.raperan.poopoo.mainservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;

import java.util.List;
import java.util.UUID;

@RequestMapping("/collections")
public interface CollectionsApi {

    /**
     * Получаем текущий трек.
     * @param collectionId
     * @return
     */
    @PostMapping("/play/{collectionId}")
    TrackBaseInfo play(@PathVariable UUID collectionId);

    /**
     * Переходим к следующему треку и получаем его.
     * @return
     */
    @PostMapping("/playNext")
    TrackBaseInfo getNext() throws TrackCollectionEnd;

    /**
     * Получаем текущий трек.
     * @return
     */
    @PostMapping("/play")
    TrackBaseInfo play();

    @GetMapping("/favoriteTracks")
    ResponseEntity<List<TrackBaseInfo>> findFavoriteTracks();

    @PostMapping("/favoriteTracks/{trackId}")
    ResponseEntity<TrackBaseInfo> startPlayFavoriteFromTrack(@PathVariable UUID trackId);

    void getCollectionsByOwner(UUID ownerId);
    void getCollectionsByOwner(String ownerEmail);

    void getCollection(UUID collectionId);

    void createCollection(String name);

    void addMusic(UUID collectionId, UUID musicId);

    void removeMusic(UUID collectionId, UUID musicId);

    void deleteCollection(UUID collectionId);

}
