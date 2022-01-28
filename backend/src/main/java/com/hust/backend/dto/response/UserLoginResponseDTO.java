package com.hust.backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private PayLoadResponseDTO payload;
}
