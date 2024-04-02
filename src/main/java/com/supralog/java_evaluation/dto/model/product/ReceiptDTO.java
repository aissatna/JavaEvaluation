package com.supralog.java_evaluation.dto.model.product;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ReceiptDTO {
    private List<ReceiptItemDTO> products = new ArrayList<>();
    private double salesTaxes;
    private double total;
}
