package ru.raperan.poopoo.mainservice.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.mainservice.api.CollectionsApi;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.service.Impl.CollectionsService;

import java.util.UUID;

@RestController
public class CollectionsController implements CollectionsApi {

    @Autowired
    private CollectionsService collectionsService;

    @Override
    public TrackBaseInfo play(UUID collectionId) {
        return collectionsService.play(collectionId);
    }

    @Override
    public TrackBaseInfo getNext(UUID collectionId) {
        return collectionsService.playNext(collectionId);
    }

    @Override
    public void getCollections(UUID ownerId) {

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
