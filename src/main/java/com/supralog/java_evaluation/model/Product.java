package com.supralog.java_evaluation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String name;
    private ProductType type;
    private boolean isImported;
}
