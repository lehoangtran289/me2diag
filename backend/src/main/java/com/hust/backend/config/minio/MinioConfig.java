package com.hust.backend.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Objects;

@Configuration
@Slf4j
@ConditionalOnProperty(value = "app.minio.enable", havingValue = "true")
@Primary
public class MinioConfig {

    @Bean
    @SuppressWarnings("java:S1141")
    public MinioClient minioClient(MinioAutoConfiguration config) {

        MinioClient minioClient;
        try {
            minioClient = MinioClient.builder()
                    .endpoint(Objects.requireNonNull(HttpUrl.parse(config.getUrl())))
                    .credentials(config.getAccessKey(), config.getSecretKey())
                    .region(config.getRegion())
                    .build();
            minioClient.setTimeout(
                    config.getConnectTimeout().toMillis(),
                    config.getWriteTimeout().toMillis(),
                    config.getReadTimeout().toMillis()
            );
        } catch (Exception e) {
            log.error("Error while connecting to Minio", e);
            throw e;
        }

        try {
            if (config.isCheckBucket()) {
                // Make bucket if not exist.
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(config.getBucket())
                        .build());
                if (!found) {
                    // Make a new bucket
                    minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(config.getBucket())
                            .build());
                }
            }
        } catch (Exception e) {
            // ignored cannot check bucket
        }
        return minioClient;
    }
}
