package com.hust.backend.application.KDclassification.service;

import com.hust.backend.application.KDclassification.dto.response.KDCResultResponseDTO;
import com.hust.backend.entity.ExaminationEntity;

public interface KDCExamService {
    KDCResultResponseDTO buildExamResult(ExaminationEntity e);
}
