package com.hust.backend.application.picturefuzzyset.dto.response;

import com.hust.backend.constant.ApplicationEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfoResponseDTO {
    private String examinationId;
    private ApplicationEnum applicationId;
    private Date date;
}

