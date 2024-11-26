package ru.raperan.poopoo.mainservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Track {
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
