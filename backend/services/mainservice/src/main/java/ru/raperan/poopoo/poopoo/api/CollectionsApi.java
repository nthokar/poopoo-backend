package ru.raperan.poopoo.poopoo.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;

import java.util.UUID;

@RequestMapping("/collections")
public interface CollectionsApi {

    @PostMapping("/{collectionId}/play")
    MusicBaseInfoDto play(@PathVariable UUID collectionId);

    void setState(UUID collectionId, UUID lastTrack);

    @PostMapping("/{collectionId}/playNext")
    MusicBaseInfoDto getNext(@PathVariable UUID collectionId);

    void getCollections(UUID ownerId);

    void getCollection(UUID collectionId);

    void createCollection(String name);

    void addMusic(UUID collectionId, UUID musicId);

    void removeMusic(UUID collectionId, UUID musicId);

    void deleteCollection(UUID collectionId);

}
