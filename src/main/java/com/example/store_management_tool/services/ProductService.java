package com.example.store_management_tool.services;

import com.example.store_management_tool.data.entities.Product;
import com.example.store_management_tool.data.ProductRequestDto;
import com.example.store_management_tool.data.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ResponseEntity<List<Product>> getAllProducts() {
        try
        {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Product> getProductById(int id) {
        try
        {
            if (id <= 0) {
                LOGGER.error("Invalid ID provided for retrieving product operation");
                return new ResponseEntity<>(new Product(), HttpStatus.BAD_REQUEST);
            }

            if (repository.findById(id).isPresent())
                return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addProduct(ProductRequestDto productDto) {
        try
        {
            if (productDto.getName().matches(".*\\d.*"))
            {
                LOGGER.error("Invalid product name provided");
                return new ResponseEntity<>("Product name should not contain any digit", HttpStatus.BAD_REQUEST);
            }

            if (productDto.getQuantity() <= 0){
                LOGGER.error("Invalid quantity provided");
                return new ResponseEntity<>("Product quantity must be greater than 0", HttpStatus.BAD_REQUEST);
            }

            Product productToBeAdded = new Product();

            if (repository.findByName(productDto.getName()).isPresent()) {
                LOGGER.error("Product already present, updating the quantity");
                productToBeAdded = repository.findByName(productDto.getName()).get();
                productToBeAdded.setQuantity(productToBeAdded.getQuantity() + productDto.getQuantity());
            } else {
                BeanUtils.copyProperties(productDto, productToBeAdded);
            }

            repository.save(productToBeAdded);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(int id) {
        try
        {
            if (id <= 0) {
                LOGGER.error("Invalid ID provided for deleting product operation");
                return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
            }

            repository.deleteById(id);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}
