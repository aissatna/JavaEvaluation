package com.supralog.java_evaluation.service.product;

import com.supralog.java_evaluation.dto.model.product.ReceiptDTO;
import com.supralog.java_evaluation.model.Product;

import java.util.List;

public interface IProductService {
    Product getProductByName(String productName);

    ReceiptDTO generateProductsReceipt(List<String> productLines);
}
