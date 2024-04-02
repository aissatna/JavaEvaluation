package com.supralog.java_evaluation.service.product;

import com.supralog.java_evaluation.configuration.exception.ElementNotFoundException;
import com.supralog.java_evaluation.dto.model.product.ReceiptDTO;
import com.supralog.java_evaluation.dto.model.product.ReceiptItemDTO;
import com.supralog.java_evaluation.model.Product;
import com.supralog.java_evaluation.model.TaxRate;
import com.supralog.java_evaluation.repository.IProductRepository;
import com.supralog.java_evaluation.util.NumberUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new ElementNotFoundException("Product not found: " + productName));

    }


    /**
     * Generates a receipt for the given list of product lines
     * @param productLines The list of product lines where each line represents a product in the format "quantity name price"
     * @return A ReceiptDTO object containing information about the products, total sales taxes, and total cost
     */
    @Override
    public ReceiptDTO generateProductsReceipt(List<String> productLines) {
        List<ReceiptItemDTO> products = new ArrayList<>();
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (String productLine : productLines) {
            String[] parts = productLine.split(" ");
            int quantity = Integer.parseInt(parts[0]);
            BigDecimal price = new BigDecimal(parts[parts.length - 1]);

            StringBuilder productNameBuilder = new StringBuilder();
            for (int i = 1; i < parts.length - 2; i++) {
                productNameBuilder.append(parts[i]);
                productNameBuilder.append(" ");
            }

            String productName = productNameBuilder.toString().trim();

            Product product = getProductByName(productName);

            BigDecimal salesTax = calculateSalesTax(product, price, quantity);
            BigDecimal totalPrice = calculateTotalPrice(product, price, quantity);

            ReceiptItemDTO receiptItemDTO = new ReceiptItemDTO()
                    .setQuantity(quantity)
                    .setName(productName)
                    .setPriceWithTax(totalPrice.doubleValue());

            products.add(receiptItemDTO);

            totalSalesTax = totalSalesTax.add(salesTax);
            totalCost = totalCost.add(totalPrice);
        }


        return new ReceiptDTO()
                .setProducts(products)
                .setSalesTaxes(totalSalesTax.doubleValue())
                .setTotal(totalCost.doubleValue());
    }


    /******************
     * PRIVATE METHODS
     *************** */

    private BigDecimal calculateSalesTax(Product product, BigDecimal price, int quantity) {
        BigDecimal taxRate = BigDecimal.valueOf(product.getType().getTaxRate());
        if (product.isImported()) {
            taxRate = taxRate.add(BigDecimal.valueOf(TaxRate.IMPORTED_TAX_RATE));
        }
        return NumberUtils.roundToNearestFiveCents(price.multiply(taxRate).multiply(BigDecimal.valueOf(quantity)));
    }

    private BigDecimal calculateTotalPrice(Product product, BigDecimal price, int quantity) {
        BigDecimal salesTax = calculateSalesTax(product, price, quantity);
        return price.multiply(BigDecimal.valueOf(quantity)).add(salesTax);

    }


}
