package ru.raperan.poopoo.poopoo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.poopoo.domain.CollectionPlayState;
import ru.raperan.poopoo.poopoo.domain.Music;
import ru.raperan.poopoo.poopoo.domain.MusicCollection;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;
import ru.raperan.poopoo.poopoo.repositories.CollectionPlayStateRepository;
import ru.raperan.poopoo.poopoo.repositories.MusicCollectionRepository;
import ru.raperan.poopoo.poopoo.service.exceptions.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionsService {

    @Autowired
    MusicCollectionRepository collectionRepository;

    @Autowired
    CollectionPlayStateRepository collectionPlayStateRepository;

    /**
     *
     * @param collectionId
     * @return
     */
    public MusicBaseInfoDto play(UUID collectionId) {
        MusicCollection collection = collectionRepository.findById(collectionId)
                                         .orElseThrow(() -> new ResourceNotFoundException("collection"));

        try {
            collectionPlayStateRepository.deleteByCollection(collectionId);
        } catch (Exception e) {

        }

        //start new playSession
        CollectionPlayState collectionPlayState = new CollectionPlayState()
                                                      .setCollection(collection)
                                                      .setLastTrack(collection
                                                                        .getMusic()
                                                                        .getFirst());
        collectionPlayStateRepository.save(collectionPlayState);

        return new MusicBaseInfoDto(
            collectionPlayState.getLastTrack().getName(),
            String.format("/audio/%s", collectionPlayState.getLastTrack().getAudioFileMeta().getFileId())
        );

    }

    public MusicBaseInfoDto playNext(UUID collectionId) {
        MusicCollection collection = collectionRepository.findById(collectionId)
                                                         .orElseThrow(() -> new ResourceNotFoundException("collection"));

        CollectionPlayState collectionPlayState = collectionPlayStateRepository.findByCollection(collection)
                                                                               .orElseThrow(() -> new ResourceNotFoundException("playState"));

        Music nextTrack = collectionPlayState.getCollection().getMusic()
                                             .stream()
                                             .dropWhile(music -> music.getId() != collectionPlayState.getLastTrack().getId())
                                             .skip(1)
                                             .findFirst()
                                             .orElseThrow(() -> new ResourceNotFoundException("nextTrack"));

        collectionPlayState.setLastTrack(nextTrack);
        collectionPlayStateRepository.save(collectionPlayState);

        return new MusicBaseInfoDto(
            collectionPlayState.getLastTrack().getName(),
            String.format("/audio/%s", collectionPlayState.getLastTrack().getAudioFileMeta().getFileId())
        );

    }



}
