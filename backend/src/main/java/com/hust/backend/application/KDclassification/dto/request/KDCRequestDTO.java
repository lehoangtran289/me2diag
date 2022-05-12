package com.hust.backend.application.KDclassification.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KDCRequestDTO implements Serializable {
    @Size(max = 50, message = "Invalid string length")
    @NotBlank(message = "patientId must not blank")
    @JsonProperty("patient_id")
    private String patientId;

    @JsonProperty("WBC")
    @NotNull
    private Object WBC;

    @JsonProperty("LY")
    @NotNull
    private Object LY;

    @JsonProperty("NE")
    @NotNull
    private Object NE;

    @JsonProperty("RBC")
    @NotNull
    private Object RBC;

    @JsonProperty("HGB")
    @NotNull
    private Object HGB;

    @JsonProperty("HCT")
    @NotNull
    private Object HCT;

    @JsonProperty("PLT")
    @NotNull
    private Object PLT;

    @JsonProperty("Na")
    @NotNull
    private Object Na;

    @JsonProperty("K")
    @NotNull
    private Object K;

    @JsonProperty("total_protein")
    @NotNull
    private Object totalProtein;

    @JsonProperty("Albumin")
    @NotNull
    private Object Albumin;

    @JsonProperty("Ure")
    @NotNull
    private Object Ure;

    @JsonProperty("Creatinin")
    @NotNull
    private Object Creatinin;

}

