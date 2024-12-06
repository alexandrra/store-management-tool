package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.dtos.ProductResponseDto;
import com.example.store_management_tool.services.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("products")
    public ResponseEntity<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "5", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "true", required = false) boolean ascending) {
        ProductResponseDto response = service.getAllProducts(pageNo, pageSize, sortBy, ascending);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id) {
        var response = service.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("category/{categoryName}")
    public ResponseEntity<ProductResponseDto> getProductsByCategory(@PathVariable String categoryName) {
        ProductResponseDto products = service.getProductsByCategory(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable int id, @RequestBody @Valid ProductRequestDto product) {
        var response = service.updateProduct(id, product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("products")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto product) {
        var response = service.addProduct(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProduct(id));
    }
}
