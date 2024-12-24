package ru.raperan.poopoo.mainservice.dto;

import lombok.Getter;
import ru.raperan.poopoo.mainservice.domain.Track;

import java.util.Objects;
import java.util.UUID;

@Getter
public class TrackBaseInfo {
    private final String name;
    private final String poster;
    private final String authorName;
    private final UUID trackId;
    private final String fileUrl;
    private final Boolean isFavorite;

    public TrackBaseInfo(
            String name,
            String poster,
            String authorName,
            UUID trackId,
            String fileUrl, Boolean isFavorite

    ) {
        this.name = name;
        this.poster = poster;
        this.authorName = authorName;
        this.trackId = trackId;
        this.fileUrl = fileUrl;
        this.isFavorite = isFavorite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TrackBaseInfo) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.poster, that.poster) &&
                Objects.equals(this.authorName, that.authorName) &&
                Objects.equals(this.fileUrl, that.fileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, poster, authorName, fileUrl);
    }

    @Override
    public String toString() {
        return "TrackBaseInfo[" +
                "name=" + name + ", " +
                "poster=" + poster + ", " +
                "authorName=" + authorName + ", " +
                "fileUrl=" + fileUrl + ']';
    }

}
