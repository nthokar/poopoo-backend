package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.dto.AlbumBaseInfo;
import ru.raperan.poopoo.mainservice.dto.AuthorBaseInfo;
import ru.raperan.poopoo.mainservice.repositories.AlbumRepository;
import ru.raperan.poopoo.mainservice.repositories.AuthorRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumService {

    @Autowired AlbumRepository albumRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired TrackService trackService;

    public Optional<AlbumBaseInfo> findAlbumBaseInfo(UUID albumId) {
        return albumRepository.findById(albumId)
                              .map(album -> new AlbumBaseInfo(album.getName(),
                                                              String.format("/image/%s", album.getPoster().getFileId()),
                                                              new AuthorBaseInfo(album.getAuthor().getName()),
                                                              trackService.findTracksBaseInfoByAlbumId(album)
                              ));
    }

}
