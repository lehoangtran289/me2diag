package com.hust.backend.application.picturefuzzyset.entity;

import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
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
@Table(name = "linguistic_domain")
public class LinguisticDomainEntity {
    @Id
    @Column(name = "name")
//    @Convert(converter = LinguisticDomainConverter.class)
    @Enumerated(EnumType.STRING)
    private LinguisticDomainEnum linguisticDomainElement;

    @Column(name = "fm")
    private Double fmValue;

    @Column(name = "v")
    private Double vValue;

    @Column(name = "order")
    private Integer order;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedAt;

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
