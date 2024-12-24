package ru.raperan.poopoo.mainservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.mainservice.domain.FileMeta;
import ru.raperan.poopoo.mainservice.domain.StorageType;
import ru.raperan.poopoo.mainservice.service.FileStorage;
import ru.raperan.poopoo.minio.service.FileStorageClient;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioFileStorage implements FileStorage {

    @Autowired
    FileStorageClient fileStorageClient;

    @Override
    public Resource getFile(FileMeta fileMeta) {

        InputStream in = fileStorageClient.find(
            String.format("%s.%s",
                          fileMeta.getFileId(),
                          fileMeta.getFileExtension()
                         )
                                               );

        try {
            return new ByteArrayResource(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StorageType getStorageType() {
        return StorageType.MINIO;
    }

}
