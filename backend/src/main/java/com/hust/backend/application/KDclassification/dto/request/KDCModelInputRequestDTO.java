package com.hust.backend.application.KDclassification.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KDCModelInputRequestDTO implements Serializable {
    @JsonProperty("Age")
    private int age;

    @JsonProperty("Gender")
    private int gender;

    @JsonProperty("WBC +")
    private Double WBC;

    @JsonProperty("LY% --")
    private Double LY;

    @JsonProperty("NE% +")
    private Double NE;

    @JsonProperty("RBC")
    private Double RBC;

    @JsonProperty("HGB")
    private Double HGB;

    @JsonProperty("HCT -")
    private Double HCT;

    @JsonProperty("PLT")
    private Double PLT;

    @JsonProperty("Na +")
    private Double Na;

    @JsonProperty("K+")
    private Double K;

    @JsonProperty("total protein")
    private Double totalProtein;

    @JsonProperty("Albumin")
    private Double Albumin;

    @JsonProperty("Ure")
    private Double Ure;

    @JsonProperty("Creatinin")
    private Double Creatinin;

}


