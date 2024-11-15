package ru.raperan.poopoo.poopoo.service;

import org.springframework.core.io.Resource;
import ru.raperan.poopoo.poopoo.domain.AudioFileMeta;
import ru.raperan.poopoo.poopoo.domain.StorageType;

public interface FileStorage {

    Resource getFile(AudioFileMeta audioFileMeta);

    StorageType getStorageType();


}
