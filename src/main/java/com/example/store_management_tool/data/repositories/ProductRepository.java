package com.example.store_management_tool.data.repositories;

import com.example.store_management_tool.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String productName);
    List<Product> findByCategory(String category);
}
