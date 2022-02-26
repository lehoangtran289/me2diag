package com.hust.backend.entity.key;

import com.hust.backend.constant.DiagnoseEnum;
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
public class SymptomDiagnoseEntityKey implements Serializable {
    private SymptomEnum symptom;
    private DiagnoseEnum diagnose;
}
