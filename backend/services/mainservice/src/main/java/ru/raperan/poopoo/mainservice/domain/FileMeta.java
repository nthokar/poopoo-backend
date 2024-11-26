package ru.raperan.poopoo.mainservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class FileMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID fileId;
    private String fileExtension;

    @Enumerated(value = EnumType.STRING)
    private StorageType storageType;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;
}
