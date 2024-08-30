package io.authreporttooltest.examplarapi.service;

import io.authreporttooltest.examplarapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        // Add some dummy data
        products.add(new Product(1L, "Laptop", 999.99));
        products.add(new Product(2L, "Smartphone", 499.99));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product createProduct(Product product) {
        product.setId((long) (products.size() + 1));
        products.add(product);
        return product;
    }
}
