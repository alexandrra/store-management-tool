package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.dtos.ProductPageResponseDto;
import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.dtos.ProductResponseDto;
import com.example.store_management_tool.services.ProductService;
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
    public ResponseEntity<ProductPageResponseDto> getAllProducts(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "5", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "true", required = false) boolean ascending) {
        try {
            ProductPageResponseDto response = service.getAllProducts(pageNo, pageSize, sortBy, ascending);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id) {
        try {
            var response = service.getProductById(id);
            if (response.getResponse() == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            else
                return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDto("Error while getting the product", "error", null));        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestParam int id, @RequestBody ProductRequestDto product) {
        try {
            var response = service.updateProduct(id, product);
            if (response.getResponse() == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            else
                return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDto("Error while updating the product", "error", null));
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("products")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto product) {
        try {
            var response = service.addProduct(product);
            if (response.getResponse() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            else
                return new ResponseEntity<>(response,HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDto("Error while adding the product", "error", null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteProduct(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductResponseDto("Error while deleting the product", "error", null));        }
    }
}
