package com.hust.backend.entity.key;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.LinguisticDomainEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class LinguisticApplicationEntityKey implements Serializable {
    @Enumerated(EnumType.STRING)
    private ApplicationEnum applicationId;

    @Enumerated(EnumType.STRING)
    private LinguisticDomainEnum linguisticDomainElement;
}
