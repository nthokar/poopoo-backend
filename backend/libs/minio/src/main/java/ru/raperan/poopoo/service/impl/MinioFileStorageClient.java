package ru.raperan.poopoo.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.raperan.poopoo.exception.DeleteFileException;
import ru.raperan.poopoo.exception.ReceiveFileException;
import ru.raperan.poopoo.exception.SaveFileException;
import ru.raperan.poopoo.service.FileStorageClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class MinioFileStorageClient implements FileStorageClient {

    private final MinioClient minioClient;
    private final String minioBucketName;

    @Override
    public void save(byte[] bytes, String id) {
        createBucket();
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            minioClient.putObject(PutObjectArgs.builder()
                                               .bucket(minioBucketName)
                                               .object(id)
                                               .stream(inputStream, bytes.length, -1)
                                               .build());
        } catch (Exception e) {
            log.warn("failed to save body to minio: {}", e.toString());
            throw new SaveFileException(e);
        }
    }

    @Override
    public InputStream find(String id) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(minioBucketName).object(id).build());
        } catch (Exception e) {
            throw new ReceiveFileException(e);
        }
    }


    public void delete(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioBucketName).object(objectName).build());
        } catch (Exception e) {
            throw new DeleteFileException(e);
        }
    }

    @SneakyThrows
    private void createBucket() {
        var found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucketName).build());

        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucketName).build());
        }
    }

}