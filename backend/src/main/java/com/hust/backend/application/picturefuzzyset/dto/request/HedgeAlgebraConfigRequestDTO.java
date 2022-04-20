package com.hust.backend.application.picturefuzzyset.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HedgeAlgebraConfigRequestDTO {
    @DecimalMax("1.0")
    @DecimalMin("0.0")
    private Double neutralTheta;

    @DecimalMax("1.0")
    @DecimalMin("0.0")
    private Double alpha;
}
