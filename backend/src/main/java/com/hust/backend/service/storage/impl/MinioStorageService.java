package com.hust.backend.service.storage.impl;

import com.hust.backend.config.minio.MinioAutoConfiguration;
import com.hust.backend.config.minio.MinioConfig;
import com.hust.backend.constant.ResourceType;
import com.hust.backend.exception.InternalException;
import com.hust.backend.exception.NotValidException;
import com.hust.backend.service.storage.StorageService;
import com.hust.backend.utils.ULID;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(MinioConfig.class)
public class MinioStorageService implements StorageService {
    private final MinioClient minioClient;
    private final MinioAutoConfiguration config;

    @Override
    public String upload(ResourceType type, MultipartFile file) {
        if (StringUtils.isBlank(file.getOriginalFilename())) {
            throw new NotValidException(MultipartFile.class, file);
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = Paths.get(type.folderName, ULID.nextULID() + "." + extension).toString();
        fileName = fileName.replace("\\", "/");
        try {
            minioClient.putObject(PutObjectArgs.builder()
                                               .bucket(config.getBucket())
                                               .contentType(file.getContentType())
                                               .stream(file.getInputStream(), file.getSize(), -1)
                                               .object(fileName)
                                               .build());
            return fileName;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Cannot upload file", e);
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public InputStream get(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                                                      .bucket(config.getBucket())
                                                      .object(fileName)
                                                      .build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Cannot download file", e);
            throw new InternalException(e.getMessage());
        }

    }

    @Override
    public boolean delete(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                                                     .bucket(config.getBucket())
                                                     .object(fileName)
                                                     .build());

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Cannot delete file", e);
            return false;
        }
        return true;
    }

    /**
     * @return List file cannot delete
     */
    @Override
    public List<String> delete(String[] fileNames) {
        if (fileNames == null || fileNames.length == 0) {
            return Collections.emptyList();
        }
        return deleteFiles(fileNames);
    }

    private List<String> deleteFiles(String[] fileNames) {
        Iterable<DeleteObject> objs =
                Arrays.stream(fileNames)
                      .map(DeleteObject::new)
                      .collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                                                                                           .bucket(config.getBucket())
                                                                                           .objects(objs)
                                                                                           .build());
        List<String> undeleted = new ArrayList<>();
        for (Result<DeleteError> result : results) {
            try {
                undeleted.add(result.get().objectName());
            } catch (Exception e) {
                log.error("Cannot delete file {}", fileNames, e);
                throw new InternalException(e.getMessage());
            }
        }
        return undeleted;
    }

    /**
     * @return List file cannot delete
     */
    @Override
    public List<String> delete(List<String> fileNames) {
        if (CollectionUtils.isEmpty(fileNames)) {
            return Collections.emptyList();
        }
        return deleteFiles(fileNames.toArray(new String[0]));
    }

    @Override
    public boolean isExist(String fileName) {
        // Get information of an object.
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                                  .bucket(config.getBucket())
                                  .object(fileName)
                                  .build());
        } catch (ErrorResponseException e) {
            if (e.errorResponse().errorCode() != ErrorCode.NO_SUCH_OBJECT) {
                return false;
            }
            log.error("Cannot check isExist return errorResponse {}", e.errorResponse().errorCode(), e);
            throw new InternalException(e.getMessage());
        } catch (Exception e) {
            log.error("Cannot check isExist {}", fileName, e);
            throw new InternalException(e.getMessage());
        }
        return true;
    }
}
