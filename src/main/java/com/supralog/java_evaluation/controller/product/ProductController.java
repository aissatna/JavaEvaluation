package com.supralog.java_evaluation.controller.product;

import com.supralog.java_evaluation.dto.model.product.ReceiptDTO;
import com.supralog.java_evaluation.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping("/receipt")
    public ResponseEntity <ReceiptDTO> generateProductsReceipt(@RequestBody List<String> productLines) {
        return ResponseEntity.ok(productService.generateProductsReceipt(productLines));
    }

}
