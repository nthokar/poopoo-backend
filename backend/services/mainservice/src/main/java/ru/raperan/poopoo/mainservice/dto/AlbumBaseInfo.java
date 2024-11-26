package ru.raperan.poopoo.mainservice.dto;

import java.util.List;

public record AlbumBaseInfo(
    String name,
    String imageUrl,
    AuthorBaseInfo author,
    List<TrackBaseInfo> tracks

) {

}
