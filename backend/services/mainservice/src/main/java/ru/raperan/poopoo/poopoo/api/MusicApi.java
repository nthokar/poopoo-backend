package ru.raperan.poopoo.poopoo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.raperan.poopoo.poopoo.domain.Album;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;

import java.util.UUID;

@RequestMapping("/music")
public interface MusicApi {

    @GetMapping("/{musicId}")
    ResponseEntity<MusicBaseInfoDto> getMusic(@PathVariable UUID musicId);

    Album getAlbum(UUID albumId);

}
