package com.supralog.java_evaluation.repository;

import com.supralog.java_evaluation.model.Product;
import com.supralog.java_evaluation.model.ProductType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements IProductRepository {
    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> getProducts() {

        products.add(new Product("book", ProductType.BOOK, false));
        products.add(new Product("movie", ProductType.OTHER, false));
        products.add(new Product("chocolate bar", ProductType.FOOD, false));

        products.add(new Product("imported box of chocolates", ProductType.FOOD, true));
        products.add(new Product("imported bottle of perfume", ProductType.OTHER, true));

        products.add(new Product("imported bottle of perfume", ProductType.OTHER, true));
        products.add(new Product("bottle of perfume", ProductType.OTHER, false));
        products.add(new Product("packet of pills", ProductType.MEDICAL, false));
        products.add(new Product("imported box of chocolates", ProductType.FOOD, true));
        return products;
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return getProducts().stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst();
    }
}
