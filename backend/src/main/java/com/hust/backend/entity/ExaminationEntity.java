package com.hust.backend.entity;

import com.hust.backend.constant.ApplicationEnum;
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
@Table(name = "examination")
public class ExaminationEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "application_id")
    @Enumerated(EnumType.STRING)
    private ApplicationEnum appId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
}
