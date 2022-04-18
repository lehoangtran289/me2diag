package com.hust.backend.application.picturefuzzyset.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "examination")
public class ExaminationEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
}
