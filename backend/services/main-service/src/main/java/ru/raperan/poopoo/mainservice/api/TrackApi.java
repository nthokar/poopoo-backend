package ru.raperan.poopoo.mainservice.api;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.mainservice.dto.AlbumBaseInfo;
import ru.raperan.poopoo.mainservice.dto.TrackBaseInfo;

import java.util.List;
import java.util.UUID;

@RequestMapping("")
public interface TrackApi {

    @GetMapping("/findTrackById/{trackId}")
    ResponseEntity<TrackBaseInfo> findTrackById(@PathVariable UUID trackId);

    @GetMapping("/findAlbumById/{albumId}")
    ResponseEntity<AlbumBaseInfo> findAlbumById(@PathVariable UUID albumId);

    @PostMapping("/setIsFavorite/{trackId}")
    ResponseEntity setIsFavorite(@PathVariable UUID trackId, @Param("isFavorite") Boolean isFavorite);

}
