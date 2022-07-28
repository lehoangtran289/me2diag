package com.hust.backend.application.KDclassification.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.application.KDclassification.constant.KDCDomainEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KDCDomainResponseDTO {
    private KDCDomainEnum name;
    @JsonProperty("min")
    private Double minValue;
    @JsonProperty("max")
    private Double maxValue;
    private String description;
}
