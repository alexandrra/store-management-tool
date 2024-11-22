package com.example.store_management_tool.services;

import com.example.store_management_tool.data.Product;
import com.example.store_management_tool.data.ProductRequestDto;
import com.example.store_management_tool.data.repositories.ProductRepository;
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
    public ProductRepository repository;

    public ResponseEntity<List<Product>> getAllProducts(){
        try
        {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Product> getProductById(int id) {
        try
        {
            if (repository.findById(id).isPresent())
                return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addProduct(ProductRequestDto productDto) {
        Product productToBeAdded = new Product();

        if (repository.findByName(productDto.getName()).isPresent()) {
            productToBeAdded = repository.findByName(productDto.getName()).get();
            productToBeAdded.setQuantity(productToBeAdded.getQuantity() + productDto.getQuantity());
        }
        else {
            BeanUtils.copyProperties(productDto, productToBeAdded);
        }

        repository.save(productToBeAdded);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(int id){
        repository.deleteById(id);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}
