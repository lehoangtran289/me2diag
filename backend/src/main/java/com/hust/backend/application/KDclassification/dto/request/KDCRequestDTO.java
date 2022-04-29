package com.hust.backend.application.KDclassification.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KDCRequestDTO {
    @Size(max = 50, message = "Invalid string length")
    @NotBlank(message = "patientId must not blank")
    private String patientId;

    @NotEmpty(message = "patientData is required")
    private List<Map.Entry<String, PictureFuzzySet>> symptoms;

}

