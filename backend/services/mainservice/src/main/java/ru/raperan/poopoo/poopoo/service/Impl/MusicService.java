package ru.raperan.poopoo.poopoo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.poopoo.dto.MusicBaseInfoDto;
import ru.raperan.poopoo.poopoo.repositories.MusicRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class MusicService {

    @Autowired
    MusicRepository musicRepository;

    public Optional<MusicBaseInfoDto> findMusicBaseInfo(UUID musicId) {
        return musicRepository.findById(musicId).map(music -> new MusicBaseInfoDto(
            music.getName(),
            String.format("/audio/%s", music.getAudioFileMeta().getFileId())
        ));
    }


}
