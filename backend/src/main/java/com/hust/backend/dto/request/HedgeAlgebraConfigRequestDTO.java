package com.hust.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.HedgeAlgebraEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HedgeAlgebraConfigRequestDTO {
    @NotNull
    private ApplicationEnum appId;

    @DecimalMax("1.0")
    @DecimalMin("0.0")
    private Double neutralTheta; //OK

//    @DecimalMax("1.0")
//    @DecimalMin("0.0")
//    private Double alpha;

    @NotNull
    private Map<HedgeAlgebraEnum, Double> configs;
}
