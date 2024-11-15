package ru.raperan.poopoo.config;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.raperan.poopoo.service.FileStorageClient;
import ru.raperan.poopoo.service.impl.MinioFileStorageClient;

@AutoConfiguration
@EnableConfigurationProperties(ru.raperan.poopoo.config.MinioConfiguration.class)
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MinioAutoConfig {

    @Bean
    public FileStorageClient bodyStorageClient(MinioClient minioClient, MinioConfiguration minioConfiguration) {
        return new MinioFileStorageClient(minioClient, minioConfiguration.getMinioBucketName());
    }

}
