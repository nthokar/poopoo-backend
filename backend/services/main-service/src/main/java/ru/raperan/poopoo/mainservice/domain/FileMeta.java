package ru.raperan.poopoo.mainservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "file_meta")
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
