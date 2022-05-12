package com.hust.backend.application.KDclassification.entity;

import com.hust.backend.application.KDclassification.constant.KDCResultEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kdc_examination_result")
public class KDCExamResultEntity {
    @Id
    @Column(name = "examination_id")
    private String examinationId;

    @Column(name = "WBC_value")
    private Double wbc;

    @Column(name = "LY_value")
    private Double ly;

    @Column(name = "NE_value")
    private Double ne;

    @Column(name = "RBC_value")
    private Double rbc;

    @Column(name = "HGB_value")
    private Double hgb;

    @Column(name = "HCT_value")
    private Double hct;

    @Column(name = "PLT_value")
    private Double plt;

    @Column(name = "NA_value")
    private Double na;

    @Column(name = "K_value")
    private Double k;

    @Column(name = "Total_Protein_value")
    private Double totalProtein;

    @Column(name = "Albumin_value")
    private Double albumin;

    @Column(name = "Ure_value")
    private Double ure;

    @Column(name = "Creatinin_value")
    private Double creatinin;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private KDCResultEnum result;

}

