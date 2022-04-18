package com.hust.backend.entity;

import com.hust.backend.constant.SymptomEnum;
import com.hust.backend.entity.key.PatientSymptomEntityKey;
import com.hust.backend.model.PictureFuzzySet;
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
@IdClass(PatientSymptomEntityKey.class)
@Table(name = "patient_symptom")
@ToString
public class PatientSymptomEntity {
    @Id
    @Column(name = "examination_id")
    @ToString.Exclude
    private String examinationId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "symptom")
    private SymptomEnum symptom;

    @Embedded
    private PictureFuzzySet pictureFuzzySet;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ToString.Exclude
    private Date createdAt;
}
