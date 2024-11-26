package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.CollectionPlayState;
import ru.raperan.poopoo.mainservice.domain.Track;
import ru.raperan.poopoo.mainservice.domain.TrackCollection;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.CollectionPlayStateRepository;
import ru.raperan.poopoo.mainservice.repositories.TrackCollectionRepository;
import ru.raperan.poopoo.mainservice.service.exceptions.ResourceNotFoundException;

import java.util.UUID;

@Service
public class CollectionsService {

    @Autowired
    TrackCollectionRepository collectionRepository;

    @Autowired
    CollectionPlayStateRepository collectionPlayStateRepository;

    /**
     *
     * @param collectionId
     * @return
     */
    public TrackBaseInfo play(UUID collectionId) {
        TrackCollection collection = collectionRepository.findById(collectionId)
                                                         .orElseThrow(() -> new ResourceNotFoundException("collection"));

        try {
            collectionPlayStateRepository.deleteByCollection(collectionId);
        } catch (Exception e) {

        }

        //start new playSession
        CollectionPlayState collectionPlayState = new CollectionPlayState()
                                                      .setCollection(collection)
                                                      .setLastTrack(collection
                                                                        .getTracks()
                                                                        .getFirst());
        collectionPlayStateRepository.save(collectionPlayState);

        return TrackBaseInfo.fromTrack(collectionPlayState.getLastTrack());
    }

    public TrackBaseInfo playNext(UUID collectionId) {
        TrackCollection collection = collectionRepository.findById(collectionId)
                                                         .orElseThrow(() -> new ResourceNotFoundException("collection"));

        CollectionPlayState collectionPlayState = collectionPlayStateRepository.findByCollection(collection)
                                                                               .orElseThrow(() -> new ResourceNotFoundException("playState"));

        Track nextTrack = collectionPlayState.getCollection().getTracks()
                                             .stream()
                                             .dropWhile(track -> track.getId() != collectionPlayState.getLastTrack().getId())
                                             .skip(1)
                                             .findFirst()
                                             .orElseThrow(() -> new ResourceNotFoundException("nextTrack"));

        collectionPlayState.setLastTrack(nextTrack);
        collectionPlayStateRepository.save(collectionPlayState);

        return TrackBaseInfo.fromTrack(collectionPlayState.getLastTrack());

    }



}
