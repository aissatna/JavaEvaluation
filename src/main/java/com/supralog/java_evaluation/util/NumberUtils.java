package com.supralog.java_evaluation.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {

    /**
     * Rounds a decimal number to the nearest 5 cents
     * @param value the BigDecimal value to round
     * @return the rounded value to the nearest multiple of five cents
     */
    public static BigDecimal roundToNearestFiveCents(BigDecimal value) {
        BigDecimal roundedValue = value.setScale(2, RoundingMode.HALF_UP);
        return roundedValue.divide(BigDecimal.valueOf(0.05), 0, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(0.05));
    }

}
