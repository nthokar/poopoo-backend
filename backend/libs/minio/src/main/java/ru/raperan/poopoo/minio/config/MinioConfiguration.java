package ru.raperan.poopoo.minio.config;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioConfiguration {

    private boolean enabled;

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String minioBucketName;

    @Bean
    @ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = false)
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }

    @Bean
    public String minioBucketName() {
        return minioBucketName;
    }

}