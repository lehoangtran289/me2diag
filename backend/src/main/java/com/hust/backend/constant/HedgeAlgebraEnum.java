package com.hust.backend.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hust.backend.application.picturefuzzyset.constant.SymptomEnum;
import com.hust.backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public enum HedgeAlgebraEnum {
    NONE("NONE"),
    MEDIUM("MEDIUM"),
    COMPLETELY("COMPLETELY"),
    HIGH("HIGH"),
    LOW("LOW"),
    SLIGHTLY("SLIGHTLY"),
    VERY("VERY"),
    LITTLE("LITTLE"),
    MORE("MORE"),
    POSSIBLE("POSSIBLE");

    private final String value;

    private static final Map<String, HedgeAlgebraEnum> map = new HashMap<>();
    static {
        for (HedgeAlgebraEnum hedgeEnum : values()) {
            map.put(hedgeEnum.getValue(), hedgeEnum);
        }
    }

    @JsonCreator
    public static HedgeAlgebraEnum from (String key) {
        return Optional.ofNullable(map.get(key))
                .orElseThrow(() -> new NotFoundException(HedgeAlgebraEnum.class, key));
    }

    @Override
    public String toString() {
        return value;
    }

    public static final List<HedgeAlgebraEnum> pfsHedges = List.of(
            HedgeAlgebraEnum.SLIGHTLY,
            HedgeAlgebraEnum.VERY
    );

    public static final List<HedgeAlgebraEnum> pfsNegativeHedges = List.of(
            HedgeAlgebraEnum.SLIGHTLY
    );

    public static final List<HedgeAlgebraEnum> pfsPositiveHedges = List.of(
            HedgeAlgebraEnum.VERY
    );

    public static final List<HedgeAlgebraEnum> kdcHedges = List.of(
            HedgeAlgebraEnum.LITTLE,
            HedgeAlgebraEnum.POSSIBLE,
            HedgeAlgebraEnum.VERY,
            HedgeAlgebraEnum.MORE
    );

    public static final List<HedgeAlgebraEnum> kdcNegativeHedges = Arrays.asList(
            HedgeAlgebraEnum.LITTLE,
            HedgeAlgebraEnum.POSSIBLE
    );

    public static final List<HedgeAlgebraEnum> kdcPositiveHedges = Arrays.asList(
            HedgeAlgebraEnum.VERY,
            HedgeAlgebraEnum.MORE
    );
}
