package ru.raperan.poopoo.mainservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class CollectionPlayState {
    @Id
    UUID ownerId;

    @OneToOne
    TrackCollection collection;

    @OneToOne
    Track lastTrack;

}
