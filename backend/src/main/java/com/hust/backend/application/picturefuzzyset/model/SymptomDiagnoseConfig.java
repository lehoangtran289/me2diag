package com.hust.backend.application.picturefuzzyset.model;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SymptomDiagnoseConfig {
    private SymptomEnum symptom;
    private DiagnoseEnum diagnose;
    private PictureFuzzySet pictureFuzzySet;
}
