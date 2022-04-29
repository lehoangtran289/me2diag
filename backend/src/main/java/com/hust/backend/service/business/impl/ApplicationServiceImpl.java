package com.hust.backend.service.business.impl;

import com.hust.backend.dto.response.ApplicationResponseDTO;
import com.hust.backend.repository.ApplicationRepository;
import com.hust.backend.service.business.ApplicationService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<ApplicationResponseDTO> getAllApplications() {
        return Transformer.listToList(
                applicationRepository.findAll(),
                appEntity -> Common.convertObject(appEntity, ApplicationResponseDTO.class)
        );
    }
}
