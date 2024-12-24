package ru.raperan.poopoo.mainservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.mainservice.api.CollectionsApi;
import ru.raperan.poopoo.mainservice.api.TrackCollectionEnd;
import ru.raperan.poopoo.mainservice.domain.Owner;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.service.Impl.AuthenticationService;
import ru.raperan.poopoo.mainservice.service.Impl.CollectionsService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CollectionsController implements CollectionsApi {

    private final CollectionsService collectionsService;
    private final AuthenticationService authenticationService;

    @Override
    public TrackBaseInfo play(UUID collectionId) {
        return collectionsService.play(collectionId);
    }

    @Override
    public TrackBaseInfo getNext() throws TrackCollectionEnd {
        return collectionsService.playNext();
    }

    @Override
    public TrackBaseInfo play() {
        return null;
    }

    @Override
    public ResponseEntity<List<TrackBaseInfo>> findFavoriteTracks() {
        Owner owner = authenticationService.getCurrentOwner();
        return ResponseEntity.ok(collectionsService.findFavoriteTracks(owner));
    }

    @Override
    public ResponseEntity<TrackBaseInfo> startPlayFavoriteFromTrack(UUID trackId) {
        var track = collectionsService.playFavoriteFromTrack(trackId);
        return ResponseEntity.ok(track);
    }

    @Override
    public void getCollectionsByOwner(UUID ownerId) {

    }

    @Override
    public void getCollectionsByOwner(String ownerEmail) {

    }

    @Override
    public void getCollection(UUID collectionId) {

    }

    @Override
    public void createCollection(String name) {

    }

    @Override
    public void addMusic(UUID collectionId, UUID musicId) {

    }

    @Override
    public void removeMusic(UUID collectionId, UUID musicId) {

    }

    @Override
    public void deleteCollection(UUID collectionId) {

    }

}
