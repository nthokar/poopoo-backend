package ru.raperan.poopoo.mainservice.dto;

import ru.raperan.poopoo.mainservice.domain.Track;

public record TrackBaseInfo(
    String name,
    Integer trackNumber,
    String fileUrl

) {

    public static TrackBaseInfo fromTrack(Track track) {
        return new TrackBaseInfo(track.getName(),
                                 track.getTrackNumber(),
                                 String.format("/audio/%s", track.getAudioFileMeta().getFileId()));

    }
}
