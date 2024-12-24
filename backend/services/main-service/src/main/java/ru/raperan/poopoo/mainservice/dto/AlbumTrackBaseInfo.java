package ru.raperan.poopoo.mainservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.raperan.poopoo.mainservice.domain.Track;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AlbumTrackBaseInfo {

    private final Integer trackNumber;
    private final String name;
    private final UUID trackId;
    private final String fileUrl;
    private final Boolean isFavorite;
}
