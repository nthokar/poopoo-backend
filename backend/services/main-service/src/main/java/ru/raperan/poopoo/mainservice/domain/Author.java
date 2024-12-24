package ru.raperan.poopoo.mainservice.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Author implements JsonPolymorphicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;


}
