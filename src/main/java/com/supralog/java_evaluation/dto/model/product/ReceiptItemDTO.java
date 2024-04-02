package com.supralog.java_evaluation.dto.model.product;

import lombok.Data;
import lombok.experimental.Accessors;



@Data
@Accessors(chain = true)
public class ReceiptItemDTO {
    private int quantity;
    private  String name ;
    private double priceWithTax;

}
