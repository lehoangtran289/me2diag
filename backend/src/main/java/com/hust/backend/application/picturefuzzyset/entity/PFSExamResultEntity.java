package com.hust.backend.application.picturefuzzyset.entity;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.entity.key.PFSExamResultEntityKey;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(PFSExamResultEntityKey.class)
@Table(name = "pfs_examination_result")
public class PFSExamResultEntity {
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
