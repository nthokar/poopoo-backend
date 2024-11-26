package ru.raperan.poopoo.mainservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.mainservice.dto.AlbumBaseInfo;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;

import java.util.UUID;

@RequestMapping("")
public interface TrackApi {

    @GetMapping("/findTrackById/{trackId}")
    ResponseEntity<TrackBaseInfo> findTrackById(@PathVariable UUID trackId);

    @GetMapping("/findAlbumById/{albumId}")
    ResponseEntity<AlbumBaseInfo> findAlbumById(@PathVariable UUID albumId);

}
