package com.hust.backend.application.picturefuzzyset.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PictureFuzzySet {
    private double positive = 0.0;
    private double neutral = 0.0;
    private double negative = 0.0;

    @Override
    public String toString() {
        return "(" + positive + ", " + neutral + ", " + negative + ")";
    }
}
