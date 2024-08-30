package io.authreporttooltest.examplarapi.controller;

import io.authreporttooltest.examplarapi.model.Product;
import io.authreporttooltest.examplarapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Product-related operations.
 * This controller uses Spring Security's @PreAuthorize annotations for role-based access control.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Inject ProductService for handling business logic
    @Autowired
    private ProductService productService;

    /**
     * Retrieve all products. Accessible to users with ROLE_ADMIN or ROLE_USER.
     *
     * @return A list of all products.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Retrieve a specific product by ID. This endpoint is publicly accessible.
     *
     * @param id The ID of the product to retrieve.
     * @return The requested Product object.
     * @throws RuntimeException if the requested product is not found.
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Create a new product. Only accessible to users with ROLE_ADMIN.
     *
     * @param product The Product object to be created, passed in the request body.
     * @return The newly created Product object.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
}
