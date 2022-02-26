package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.UserRoleEnum;
import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayLoadResponseDTO {
    private String subject;
    private String jti;
    private String email;
    private String env;
    private long iat;
    private long exp;
    private List<UserRoleEnum> roles;

    public static PayLoadResponseDTO toDTO(Claims claims) {
        return PayLoadResponseDTO.builder()
                                 .subject(claims.getSubject())
                                 .jti(claims.getId())
                                 .email(claims.get("email", String.class))
                                 .env(claims.get("env", String.class))
                                 .roles(claims.get("roles", ArrayList.class))
                                 .iat(claims.getIssuedAt().getTime())
                                 .exp(claims.getExpiration().getTime())
                                 .build();
    }
}
