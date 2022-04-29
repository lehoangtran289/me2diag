package com.hust.backend.entity;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.HedgeAlgebraEnum;
import com.hust.backend.constant.HedgeAlgebraTypeEnum;
import com.hust.backend.entity.key.HedgeApplicationEntityKey;
import com.hust.backend.utils.Common;
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
@IdClass(HedgeApplicationEntityKey.class)
@Table(name = "hedge_algebra_config")
public class HedgeAlgebraEntity {
    @Id
    @Column(name = "application_id")
    @Enumerated(EnumType.STRING)
    private ApplicationEnum applicationId;

    @Id
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private HedgeAlgebraEnum hedgeAlgebraEnum;

    @Column(name = "fm")
    private Double fmValue;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private HedgeAlgebraTypeEnum hedgeAlgebraTypeEnum;

    @Column(name = "linguistic_order")
    private Integer linguisticOrder;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;

    public void setFmValue(Double fmValue) {
        this.fmValue = Common.round(fmValue, 3);
    }
}
