package ru.raperan.poopoo.poopoo.service.Impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.raperan.poopoo.poopoo.domain.AudioFileMeta;
import ru.raperan.poopoo.poopoo.domain.StorageType;
import ru.raperan.poopoo.poopoo.service.FileStorage;
import ru.raperan.poopoo.service.FileStorageClient;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioFileStorage implements FileStorage {

    @Autowired
    FileStorageClient fileStorageClient;

    @Override
    public Resource getFile(AudioFileMeta audioFileMeta) {

        InputStream in = fileStorageClient.find(
            String.format("%s.%s",
                          audioFileMeta.getFileId(),
                          audioFileMeta.getFileExtension()
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
