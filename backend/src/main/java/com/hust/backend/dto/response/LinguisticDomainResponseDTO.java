package com.hust.backend.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.LinguisticDomainEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LinguisticDomainResponseDTO {
    private LinguisticDomainEnum linguisticDomainElement;
    private Double fmValue;
    private Double vValue;
    private Integer linguisticOrder;
}
