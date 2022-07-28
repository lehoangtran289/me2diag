package com.hust.backend.application.KDclassification.entity;

import com.hust.backend.application.KDclassification.constant.KDCDomainEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kdc_domain")
public class KDCDomainEntity {
    @Id
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private KDCDomainEnum name;

    @Column(name = "min")
    private Double minValue;

    @Column(name = "max")
    private Double maxValue;

    @Column(name = "description")
    private String description;
}
