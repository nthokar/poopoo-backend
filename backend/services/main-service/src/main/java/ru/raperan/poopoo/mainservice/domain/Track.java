package ru.raperan.poopoo.mainservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "track")
public class Track implements JsonPolymorphicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;

    @ManyToOne
    Author author;

    @OneToOne
    FileMeta audioFileMeta;

    @ManyToOne
    Album album;
    Integer trackNumber;

    @ManyToMany
    List<Author> feats;

}
