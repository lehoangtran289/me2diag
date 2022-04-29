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

    @Column(name = "WBC")
    private Double wbc;

    @Column(name = "LY")
    private Double ly;

    @Column(name = "NE")
    private Double ne;

    @Column(name = "RBC")
    private Double rbc;

    @Column(name = "HGB")
    private Double hgb;

    @Column(name = "HCT")
    private Double hct;

    @Column(name = "PLT")
    private Double plt;

    @Column(name = "NA")
    private Double na;

    @Column(name = "K")
    private Double k;

    @Column(name = "Total protein")
    private Double totalProtein;

    @Column(name = "Albumin")
    private Double albumin;

    @Column(name = "Ure")
    private Double ure;

    @Column(name = "Creatinin")
    private Double creatinin;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private KDCResultEnum result;

}

