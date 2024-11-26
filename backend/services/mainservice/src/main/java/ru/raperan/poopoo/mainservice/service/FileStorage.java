package ru.raperan.poopoo.mainservice.service;

import org.springframework.core.io.Resource;
import ru.raperan.poopoo.mainservice.domain.FileMeta;
import ru.raperan.poopoo.mainservice.domain.StorageType;

public interface FileStorage {

    Resource getFile(FileMeta fileMeta);

    StorageType getStorageType();


}
