package com.supralog.java_evaluation.model;

public enum ProductType {
    BOOK, FOOD, MEDICAL, OTHER;
    public double getTaxRate() {
        return switch (this) {
            case BOOK, FOOD, MEDICAL -> TaxRate.EXEMPTED_TAX_RATE;
            default -> TaxRate.STANDARD_TAX_RATE;
        };
    }

}
