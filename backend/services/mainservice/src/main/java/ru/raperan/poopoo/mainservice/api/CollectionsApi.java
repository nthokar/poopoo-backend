package ru.raperan.poopoo.mainservice.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;

import java.util.UUID;

@RequestMapping("/collections")
public interface CollectionsApi {

    @PostMapping("/{collectionId}/play")
    TrackBaseInfo play(@PathVariable UUID collectionId);

    @PostMapping("/{collectionId}/playNext")
    TrackBaseInfo getNext(@PathVariable UUID collectionId);

    void getCollections(UUID ownerId);

    void getCollection(UUID collectionId);

    void createCollection(String name);

    void addMusic(UUID collectionId, UUID musicId);

    void removeMusic(UUID collectionId, UUID musicId);

    void deleteCollection(UUID collectionId);

}
