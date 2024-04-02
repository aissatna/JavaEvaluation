package com.supralog.java_evaluation.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaxRate {
    public static final double EXEMPTED_TAX_RATE = 0.0;
    public static final double STANDARD_TAX_RATE = 0.1;
    public static final double IMPORTED_TAX_RATE = 0.05;

}
