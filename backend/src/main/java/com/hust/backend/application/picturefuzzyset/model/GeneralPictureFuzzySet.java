package com.hust.backend.application.picturefuzzyset.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GeneralPictureFuzzySet implements Serializable {
    private transient Object positive;
    private transient Object neutral;
    private transient Object negative;

    @Override
    public String toString() {
        return "(" + positive + ", " + neutral + ", " + negative + ")";
    }
}

