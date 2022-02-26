package com.hust.backend.service.business;

import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.dto.response.DiagnoseResponseDTO;
import com.hust.backend.model.PictureFuzzySet;
import com.hust.backend.utils.tuple.Tuple2;

import java.util.List;

public interface PictureFuzzyRelationService {
    DiagnoseResponseDTO diagnose(String userId, String patientId, List<Tuple2<SymptomEnum, PictureFuzzySet>> symptoms);
}
