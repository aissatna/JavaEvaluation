package com.supralog.java_evaluation.service.product;

import com.supralog.java_evaluation.configuration.exception.ElementNotFoundException;
import com.supralog.java_evaluation.dto.model.product.ReceiptDTO;
import com.supralog.java_evaluation.model.Product;
import com.supralog.java_evaluation.model.ProductType;
import com.supralog.java_evaluation.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void getProductByName_WithValidExistingName_ShouldReturnProduct() {
        // Arrange
        Product testProduct = new Product("book", ProductType.BOOK,false);
        when(productRepository.findByName("book")).thenReturn(Optional.of(testProduct));
        // Act & Assert
        assertEquals(testProduct, productService.getProductByName("book"));
    }

    @Test
    void getProductByName_WithNotExistingName_ShouldThrowElementNotFoundException() {
        // Arrange
        when(productRepository.findByName("test")).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> productService.getProductByName("test"));
    }


    @Test
    void generateProductsReceipt_WithNotImportedProducts_ShouldCalculateCorrectTaxesAndTotal() {
        // Arrange
        List<String> productLines = List.of(
                "1 book at 12.49",
                "1 music CD at 14.99",
                "1 chocolate bar at 0.85"
        );
        Product book = new Product("book", ProductType.BOOK, false);
        Product musicCd = new Product("music CD", ProductType.OTHER, false);
        Product chocolateBar = new Product("chocolate bar", ProductType.FOOD, false);
        when(productRepository.findByName("book")).thenReturn(Optional.of(book));
        when(productRepository.findByName("music CD")).thenReturn(Optional.of(musicCd));
        when(productRepository.findByName("chocolate bar")).thenReturn(Optional.of(chocolateBar));

        // Act
        ReceiptDTO receipt = productService.generateProductsReceipt(productLines);

        // Assert
        assertEquals(3, receipt.getProducts().size());
        assertEquals(1, receipt.getProducts().getFirst().getQuantity());
        assertEquals("book", receipt.getProducts().get(0).getName());
        assertEquals(12.49, receipt.getProducts().get(0).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(1).getQuantity());
        assertEquals("music CD", receipt.getProducts().get(1).getName());
        assertEquals(16.49, receipt.getProducts().get(1).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(2).getQuantity());
        assertEquals("chocolate bar", receipt.getProducts().get(2).getName());
        assertEquals(0.85, receipt.getProducts().get(2).getPriceWithTax(), 0.01);
        assertEquals(1.5, receipt.getSalesTaxes(), 0.01);
        assertEquals(29.83, receipt.getTotal(), 0.01);
    }

    @Test
    void generateProductsReceipt_WithImportedProducts_ShouldCalculateCorrectTaxesAndTotal() {
        // Arrange
        List<String> productLines = List.of(
                "1 imported box of chocolates at 10.00",
                "1 imported bottle of perfume at 47.50"
        );
        Product importedChocolates = new Product("imported box of chocolates", ProductType.FOOD, true);
        Product importedPerfume = new Product("imported bottle of perfume", ProductType.OTHER, true);
        when(productRepository.findByName("imported box of chocolates")).thenReturn(Optional.of(importedChocolates));
        when(productRepository.findByName("imported bottle of perfume")).thenReturn(Optional.of(importedPerfume));

        // Act
        ReceiptDTO receipt = productService.generateProductsReceipt(productLines);

        // Assert
        assertEquals(2, receipt.getProducts().size());
        assertEquals(1, receipt.getProducts().getFirst().getQuantity());
        assertEquals("imported box of chocolates", receipt.getProducts().get(0).getName());
        assertEquals(10.5, receipt.getProducts().get(0).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(1).getQuantity());
        assertEquals("imported bottle of perfume", receipt.getProducts().get(1).getName());
        assertEquals(54.65, receipt.getProducts().get(1).getPriceWithTax(), 0.01);
        assertEquals(7.65, receipt.getSalesTaxes(), 0.01);
        assertEquals(65.15, receipt.getTotal(), 0.01);
    }

    @Test
    void generateProductsReceipt_WithMixedProducts_ShouldCalculateCorrectTaxesAndTotal() {
        // Arrange
        List<String> productLines = List.of(
                "1 imported bottle of perfume at 27.99",
                "1 bottle of perfume at 18.99",
                "1 packet of pills at 9.75",
                "1 imported box of chocolates at 11.25"
        );
        Product importedPerfume = new Product("imported bottle of perfume", ProductType.OTHER, true);
        Product perfume = new Product("bottle of perfume", ProductType.OTHER, false);
        Product pills = new Product("packet of pills", ProductType.MEDICAL, false);
        Product importedChocolates = new Product("imported box of chocolates", ProductType.FOOD, true);

        when(productRepository.findByName("imported bottle of perfume")).thenReturn(Optional.of(importedPerfume));
        when(productRepository.findByName("bottle of perfume")).thenReturn(Optional.of(perfume));
        when(productRepository.findByName("packet of pills")).thenReturn(Optional.of(pills));
        when(productRepository.findByName("imported box of chocolates")).thenReturn(Optional.of(importedChocolates));

        // Act
        ReceiptDTO receipt = productService.generateProductsReceipt(productLines);

        // Assert
        assertEquals(4, receipt.getProducts().size());
        assertEquals(1, receipt.getProducts().getFirst().getQuantity());
        assertEquals("imported bottle of perfume", receipt.getProducts().get(0).getName());
        assertEquals(32.19, receipt.getProducts().get(0).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(1).getQuantity());
        assertEquals("bottle of perfume", receipt.getProducts().get(1).getName());
        assertEquals(20.89, receipt.getProducts().get(1).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(2).getQuantity());
        assertEquals("packet of pills", receipt.getProducts().get(2).getName());
        assertEquals(9.75, receipt.getProducts().get(2).getPriceWithTax(), 0.01);
        assertEquals(1, receipt.getProducts().get(3).getQuantity());
        assertEquals("imported box of chocolates", receipt.getProducts().get(3).getName());
        assertEquals(11.8, receipt.getProducts().get(3).getPriceWithTax(), 0.01);
        assertEquals(6.65, receipt.getSalesTaxes(), 0.01);
        assertEquals(74.63, receipt.getTotal(), 0.01);
    }

}