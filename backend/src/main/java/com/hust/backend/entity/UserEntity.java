package com.hust.backend.entity;

import com.hust.backend.constant.UserGenderEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "password_token")
    private String passwordToken;

    @Column(name = "is_enable")
    private String isEnable;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar")
    private String avatarUrl;

    @Column(name = "birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

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
