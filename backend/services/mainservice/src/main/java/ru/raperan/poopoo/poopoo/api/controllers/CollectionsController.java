package ru.raperan.poopoo.poopoo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.poopoo.api.CollectionsApi;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;
import ru.raperan.poopoo.poopoo.service.Impl.CollectionsService;

import java.util.UUID;

@RestController
public class CollectionsController implements CollectionsApi {

    @Autowired
    private CollectionsService collectionsService;

    @Override
    public MusicBaseInfoDto play(UUID collectionId) {
        return collectionsService.play(collectionId);
    }

    @Override
    public void setState(UUID collectionId, UUID lastTrack) {

    }

    @Override
    public MusicBaseInfoDto getNext(UUID collectionId) {
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
