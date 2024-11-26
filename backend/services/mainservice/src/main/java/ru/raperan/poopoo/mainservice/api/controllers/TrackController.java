package ru.raperan.poopoo.mainservice.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.mainservice.api.TrackApi;
import ru.raperan.poopoo.mainservice.dto.AlbumBaseInfo;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;
import ru.raperan.poopoo.mainservice.service.Impl.AlbumService;
import ru.raperan.poopoo.mainservice.service.Impl.TrackService;

import java.util.UUID;

@RestController
public class TrackController implements TrackApi {


    @Autowired
    TrackService trackService;

    @Autowired
    AlbumService albumService;


    @Override
    public ResponseEntity<TrackBaseInfo> findTrackById(UUID trackId) {
        return ResponseEntity.of(trackService.findTrackBaseInfo(trackId));
    }

    @Override
    public ResponseEntity<AlbumBaseInfo> findAlbumById(UUID albumId) {
        return ResponseEntity.of(albumService.findAlbumBaseInfo(albumId));
    }

}
