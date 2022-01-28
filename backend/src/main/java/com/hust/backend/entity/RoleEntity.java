package com.hust.backend.entity;

import com.hust.backend.constant.UserRoleEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum roleEnum;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;
}
