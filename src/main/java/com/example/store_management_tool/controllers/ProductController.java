package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.Product;
import com.example.store_management_tool.data.ProductRequestDto;
import com.example.store_management_tool.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        return service.getProductById(id);
    }

    @PostMapping("product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDto product){
        return service.addProduct(product);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }
}
