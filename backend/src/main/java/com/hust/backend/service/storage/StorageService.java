package com.hust.backend.service.storage;

import com.hust.backend.constant.ResourceType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StorageService {
    String upload(ResourceType type, MultipartFile multipartFile);

    InputStream get(String fileName);

    boolean delete(String fileName);

    List<String> delete(String[] fileName);

    List<String> delete(List<String> fileName);

    boolean isExist(String fileName);
}
