package com.hust.backend.service.business;

import com.hust.backend.dto.response.ApplicationResponseDTO;

import java.util.List;

public interface ApplicationService {
    List<ApplicationResponseDTO> getAllApplications();
}
