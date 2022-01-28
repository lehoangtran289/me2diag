package com.hust.backend.entity;

import com.hust.backend.entity.key.UserRoleEntityKey;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@IdClass(UserRoleEntityKey.class)
@Table(name = "user_role")
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return userId != null && Objects.equals(userId, that.userId)
                && roleId != null && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
