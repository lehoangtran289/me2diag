package com.hust.backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenewTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}

