package com.hust.backend.entity;

import com.hust.backend.constant.DiagnoseEnum;
import com.hust.backend.entity.key.ExaminationResultEntityKey;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(ExaminationResultEntityKey.class)
@Table(name = "examination_result")
public class ExaminationResultEntity {
    @Id
    @Column(name = "examination_id")
    private String examinationId;

    @Id
    @Column(name = "diagnose")
    @Enumerated(EnumType.STRING)
    private DiagnoseEnum diagnose;

    @Column(name = "probability")
    private double probability;

}
