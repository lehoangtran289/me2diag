package com.hust.backend.controller.restful.external;

import com.hust.backend.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Slf4j
@Validated
@RestController
public class MediaController {

    private final StorageService storageService;

    public MediaController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(
            value = "/media/{resource}/{fileName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public byte[] getImage(
            @PathVariable @NotBlank(message = "resource must not be blank") String resource,
            @PathVariable @NotBlank(message = "file name must not be blank") String fileName
    ) throws IOException {
        String object = resource + "/" + fileName;
        return IOUtils.toByteArray(storageService.get(object));
    }

}
