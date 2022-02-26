package com.hust.backend.dto.response;

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

