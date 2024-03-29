package com.hust.backend.entity;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.LinguisticDomainEnum;
import com.hust.backend.entity.key.LinguisticApplicationEntityKey;
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
@IdClass(LinguisticApplicationEntityKey.class)
@Table(name = "linguistic_domain")
public class LinguisticDomainEntity {
    @Id
    @Column(name = "application_id")
    @Enumerated(EnumType.STRING)
    private ApplicationEnum applicationId;

    @Id
    @Column(name = "name")
//    @Convert(converter = LinguisticDomainConverter.class)
    @Enumerated(EnumType.STRING)
    private LinguisticDomainEnum linguisticDomainElement;

    @Column(name = "fm")
    private Double fmValue;

    @Column(name = "v")
    private Double vValue;

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

    public void setVValue(Double vValue) {
        this.vValue = Common.round(vValue, 3);
    }

    public void setFmValue(Double fmValue) {
        this.fmValue = Common.round(fmValue, 3);
    }

    //    @Converter(autoApply = true)
//    public static class LinguisticDomainConverter implements AttributeConverter<LinguisticDomainEnum, String> {
//        @Override
//        public String convertToDatabaseColumn(LinguisticDomainEnum attribute) {
//            if (attribute == null)
//                return null;
//
//            return attribute.getValue();
//        }
//
//        @Override
//        public LinguisticDomainEnum convertToEntityAttribute(String dbData) {
//            return LinguisticDomainEnum.from(dbData);
//        }
//    }
}
