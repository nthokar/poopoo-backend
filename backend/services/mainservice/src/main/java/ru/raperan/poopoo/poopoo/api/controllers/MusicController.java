package ru.raperan.poopoo.poopoo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.raperan.poopoo.poopoo.api.MusicApi;
import ru.raperan.poopoo.poopoo.domain.Album;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;
import ru.raperan.poopoo.poopoo.service.Impl.MusicService;

import java.util.UUID;

@RestController
public class MusicController implements MusicApi {


    @Autowired
    MusicService musicService;

    @Override
    public ResponseEntity<MusicBaseInfoDto> getMusic(UUID musicId) {
        return ResponseEntity.of(musicService.findMusicBaseInfo(musicId));
    }

    @Override
    public Album getAlbum(UUID albumId) {
        return null;
    }

}
