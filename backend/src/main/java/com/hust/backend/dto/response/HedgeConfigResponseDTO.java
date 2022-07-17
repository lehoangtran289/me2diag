package com.hust.backend.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.HedgeAlgebraEnum;
import com.hust.backend.constant.HedgeAlgebraTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HedgeConfigResponseDTO {
    private Double fmValue;
    private HedgeAlgebraEnum hedgeAlgebraEnum;
    private HedgeAlgebraTypeEnum hedgeAlgebraTypeEnum;
    private Integer linguisticOrder;
}

