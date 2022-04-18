package com.hust.backend.application.picturefuzzyset.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamIDListResponseDTO {
    private String examinationId;
    private Date date;
}

