package ru.raperan.poopoo.mainservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.Album;
import ru.raperan.poopoo.mainservice.domain.Track;
import ru.raperan.poopoo.mainservice.dto.AlbumTrackBaseInfo;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.TrackCollectionRepository;
import ru.raperan.poopoo.mainservice.repositories.TrackRepository;
import ru.raperan.poopoo.mainservice.service.exceptions.ResourceNotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AuthenticationService authenticationService;
    private final TrackCollectionRepository collectionRepository;


    public Optional<TrackBaseInfo> findTrackBaseInfo(UUID tackId) {
        return trackRepository.findById(tackId)
                .map(track -> new TrackBaseInfo(
                        track.getName(),
                        String.format("/image/%s", track.getAlbum().getPoster().getFileId()),
                        track.getAuthor().getName(),
                        track.getId(),
                        String.format("/audio/%s", track.getAudioFileMeta().getFileId()),
                        authenticationService.getCurrentOwner().getFavoriteTracks().getTracks().contains(track)
                        )
                );
    }

    public List<AlbumTrackBaseInfo> findTracksBaseInfoByAlbumId(Album album) {
        return trackRepository.findByAlbum(album).stream()
                              .map(this::getAlbumTrackBaseInfo)
                              .sorted(Comparator.comparing(AlbumTrackBaseInfo::getTrackNumber))
                              .toList();
    }

    public TrackBaseInfo getTrackBaseInfo(Track track) {
        return new TrackBaseInfo(
                track.getName(),
                String.format("/image/%s", track.getAlbum().getPoster().getFileId()),
                track.getAuthor().getName(),
                track.getId(),
                String.format("/audio/%s", track.getAudioFileMeta().getFileId()),
                authenticationService.getCurrentOwner().getFavoriteTracks().getTracks().contains(track)
                );
    }

    public AlbumTrackBaseInfo getAlbumTrackBaseInfo(Track track) {
        return new AlbumTrackBaseInfo(
                track.getTrackNumber(),
                track.getName(),
                track.getId(),
                String.format("/audio/%s", track.getAudioFileMeta().getFileId()),
                authenticationService.getCurrentOwner().getFavoriteTracks().getTracks().contains(track)
                );
    }

    public void setIsFavorite(UUID tackId, boolean favorite) {
        var track = trackRepository.findById(tackId).orElseThrow(() -> new ResourceNotFoundException("track"));
        var favoriteTracks = authenticationService.getCurrentOwner().getFavoriteTracks();
        if (favorite) {
            if (!favoriteTracks.getTracks().contains(track))
                favoriteTracks.getTracks().add(track);
        } else {
            favoriteTracks.getTracks().remove(track);
        }
        collectionRepository.save(favoriteTracks);
    }

}
