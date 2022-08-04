package com.hust.backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopUserInfoResponseDTO {
    private UserInfoResponseDTO user;
    private Long totalExams;
}
