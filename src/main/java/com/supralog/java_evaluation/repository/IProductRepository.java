package com.supralog.java_evaluation.repository;

import com.supralog.java_evaluation.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    List<Product> getProducts();
    Optional<Product> findByName(String productName);

}
