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
    private String wbc;

    @Column(name = "LY_value")
    private String ly;

    @Column(name = "NE_value")
    private String ne;

    @Column(name = "RBC_value")
    private String rbc;

    @Column(name = "HGB_value")
    private String hgb;

    @Column(name = "HCT_value")
    private String hct;

    @Column(name = "PLT_value")
    private String plt;

    @Column(name = "NA_value")
    private String na;

    @Column(name = "K_value")
    private String k;

    @Column(name = "Total_Protein_value")
    private String totalProtein;

    @Column(name = "Albumin_value")
    private String albumin;

    @Column(name = "Ure_value")
    private String ure;

    @Column(name = "Creatinin_value")
    private String creatinin;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private KDCResultEnum result;

}

