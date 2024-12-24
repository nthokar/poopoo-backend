package ru.raperan.poopoo.mainservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.api.TrackCollectionEnd;
import ru.raperan.poopoo.mainservice.domain.CollectionPlayState;
import ru.raperan.poopoo.mainservice.domain.Owner;
import ru.raperan.poopoo.mainservice.domain.Track;
import ru.raperan.poopoo.mainservice.domain.TrackCollection;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.CollectionPlayStateRepository;
import ru.raperan.poopoo.mainservice.repositories.TrackCollectionRepository;
import ru.raperan.poopoo.mainservice.service.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionsService {

    private final TrackCollectionRepository collectionRepository;

    private final CollectionPlayStateRepository collectionPlayStateRepository;

    private final AuthenticationService authenticationService;
    private final TrackService trackService;

    /**
     *
     * @param collectionId
     * @return
     */
    public TrackBaseInfo play(UUID collectionId) {
        var owner = authenticationService.getCurrentOwner();
        TrackCollection collection = collectionRepository.findById(collectionId)
                                                         .orElseThrow(() -> new ResourceNotFoundException("collection"));
        try {
            collectionPlayStateRepository.deleteByOwnerId(owner.getId());
        } catch (Exception e) {

        }

        //start new playSession
        CollectionPlayState collectionPlayState = new CollectionPlayState()
                .setOwnerId(owner.getId())
                .setCollection(collection)
                .setLastTrack(collection
                        .getTracks()
                        .getFirst());
        collectionPlayStateRepository.save(collectionPlayState);


        return trackService.getTrackBaseInfo(collectionPlayState.getLastTrack());
    }

    public TrackBaseInfo playNext() throws TrackCollectionEnd {
        var owner = authenticationService.getCurrentOwner();

        CollectionPlayState collectionPlayState = collectionPlayStateRepository.findByOwnerId(owner.getId())
                                                                               .orElseThrow(() -> new ResourceNotFoundException("playState"));

        Track nextTrack = collectionPlayState.getCollection().getTracks()
                                             .stream()
                                             .dropWhile(track -> track.getId() != collectionPlayState.getLastTrack().getId())
                                             .skip(1)
                                             .findFirst()
                                             .orElseThrow(TrackCollectionEnd::new);

        collectionPlayState.setLastTrack(nextTrack);
        collectionPlayStateRepository.save(collectionPlayState);

        return trackService.getTrackBaseInfo(collectionPlayState.getLastTrack());

    }

    public TrackBaseInfo playFavoriteFromTrack(UUID trackId) {
        var owner = authenticationService.getCurrentOwner();
        TrackCollection collection = owner.getFavoriteTracks();

        try {
            collectionPlayStateRepository.deleteByOwnerId(owner.getId());
        } catch (Exception e) {

        }

        //start new playSession
        CollectionPlayState collectionPlayState = new CollectionPlayState()
                .setOwnerId(owner.getId())
                .setCollection(collection)
                .setLastTrack(collection
                        .getTracks()
                        .stream().filter(track -> track.getId().equals(trackId))
                        .findFirst().orElseThrow(() -> new ResourceNotFoundException("track"))
                );
        collectionPlayStateRepository.save(collectionPlayState);

        return trackService.getTrackBaseInfo(collectionPlayState.getLastTrack());
    }

    public List<TrackBaseInfo> findFavoriteTracks(Owner owner) {
        return collectionRepository.findById(owner.getFavoriteTracks().getId())
                .orElseThrow(() -> new ResourceNotFoundException("fav"))
                .getTracks()
                .stream()
                .map(trackService::getTrackBaseInfo)
                .toList();
    }


}
