package com.hust.backend.application.picturefuzzyset.entity.key;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class ExaminationResultEntityKey implements Serializable {
    private String examinationId;
    private DiagnoseEnum diagnose;
}

