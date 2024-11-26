package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.Album;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.TrackRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackService {

    @Autowired
    TrackRepository trackRepository;

    public Optional<TrackBaseInfo> findTrackBaseInfo(UUID tackId) {
        return trackRepository.findById(tackId)
                              .map(TrackBaseInfo::fromTrack);
    }

    public List<TrackBaseInfo> findTracksBaseInfoByAlbumId(Album album) {
        return trackRepository.findByAlbum(album).stream()
                              .map(TrackBaseInfo::fromTrack)
                              .sorted(Comparator.comparing(TrackBaseInfo::trackNumber))
                              .toList();
    }


}
