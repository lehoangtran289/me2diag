package com.hust.backend.application.picturefuzzyset.entity;

import com.hust.backend.application.picturefuzzyset.constant.DiagnoseEnum;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.application.picturefuzzyset.entity.key.SymptomDiagnoseEntityKey;
import com.hust.backend.application.picturefuzzyset.model.PictureFuzzySet;
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
@ToString
@IdClass(SymptomDiagnoseEntityKey.class)
@Table(name = "symptom_diagnose")
public class SymptomDiagnoseEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "symptom")
    private SymptomEnum symptom;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "diagnose")
    private DiagnoseEnum diagnose;

    @Embedded
    private PictureFuzzySet pictureFuzzySet;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
}
