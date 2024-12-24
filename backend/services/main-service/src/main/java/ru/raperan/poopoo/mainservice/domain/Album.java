package ru.raperan.poopoo.mainservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Album implements JsonPolymorphicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    @OneToOne
    FileMeta poster;

    @ManyToOne
    Author author;

}
