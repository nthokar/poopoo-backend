package ru.raperan.poopoo.mainservice.dto;

import ru.raperan.poopoo.mainservice.domain.Album;

import java.util.List;
import java.util.UUID;


public record AlbumBaseInfo(
    UUID id,
    String name,
    String poster,
    AuthorBaseInfo author,
    List<AlbumTrackBaseInfo> tracks

) {

    public static AlbumBaseInfo fromAlbum(Album album, List<AlbumTrackBaseInfo> tracks) {
        return new AlbumBaseInfo(
                album.getId(),
                album.getName(),
                String.format("/image/%s", album.getPoster().getFileId()),
                AuthorBaseInfo.fromAuthor(album.getAuthor()),
                tracks
        );
    }
}
