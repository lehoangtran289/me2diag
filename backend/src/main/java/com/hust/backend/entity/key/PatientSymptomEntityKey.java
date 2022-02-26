package com.hust.backend.entity.key;

import com.hust.backend.constant.SymptomEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class PatientSymptomEntityKey implements Serializable {
    private String examinationId;
    private String patientId;
    private SymptomEnum symptom;
}
