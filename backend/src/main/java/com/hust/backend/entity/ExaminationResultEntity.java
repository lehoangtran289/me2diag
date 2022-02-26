package com.hust.backend.entity;

import com.hust.backend.constant.DiagnoseEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "examination_result")
public class ExaminationResultEntity {
    @Id
    @Column(name = "examination_id")
    private String examinationId;

    @Column(name = "diagnose")
    @Enumerated(EnumType.STRING)
    private DiagnoseEnum diagnose;

    @Column(name = "probability")
    private double probability;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
}
