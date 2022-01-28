package com.hust.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.ArrayList;
import java.util.Date;
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
    private List<UserRoleEnum> roles;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date iat;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date exp;

    public static PayLoadResponseDTO toDTO(Claims claims) {
        return PayLoadResponseDTO.builder()
                                 .subject(claims.getSubject())
                                 .jti(claims.getId())
                                 .email(claims.get("email", String.class))
                                 .env(claims.get("env", String.class))
                                 .roles(claims.get("roles", ArrayList.class))
                                 .iat(claims.getIssuedAt())
                                 .exp(claims.getExpiration())
                                 .build();
    }
}
