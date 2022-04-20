package com.hust.backend.application.picturefuzzyset.dto.response;

import com.hust.backend.application.picturefuzzyset.constant.LinguisticDomainEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinguisticDomainDTO {
    private LinguisticDomainEnum linguisticDomainElement;
    private Double fmValue;
    private Double vValue;
    private Integer order;
}
