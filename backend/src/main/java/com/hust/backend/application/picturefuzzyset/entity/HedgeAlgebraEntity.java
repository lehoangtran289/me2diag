package com.hust.backend.application.picturefuzzyset.entity;

import com.hust.backend.application.picturefuzzyset.constant.HedgeAlgebraEnum;
import com.hust.backend.application.picturefuzzyset.constant.HedgeAlgebraTypeEnum;
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
@Table(name = "hedge_algebra_config")
public class HedgeAlgebraEntity {
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
