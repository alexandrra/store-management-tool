package com.example.store_management_tool.services;

import com.example.store_management_tool.data.dtos.ProductPageResponseDto;
import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.dtos.ProductResponseDto;
import com.example.store_management_tool.data.entities.Product;
import com.example.store_management_tool.data.repositories.ProductRepository;
import com.example.store_management_tool.utils.Constants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        mapper = new ModelMapper();
    }

    public ProductPageResponseDto getAllProducts(int pageNo, int pageSize, String sortBy, boolean ascending) {
        try {
            // Retrieve product list on pagination limits
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            Page<Product> fetchedProducts = repository.findAll(pageable);

            return new ProductPageResponseDto(Constants.GET_ALL_PRODUCTS_SUCCESS, "success", fetchedProducts, pageNo, pageSize, fetchedProducts.getSize());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto getProductById(int id) {
        try {
            Optional<Product> product = repository.findById(id);

            if (product.isEmpty())
                return new ProductResponseDto(Constants.PRODUCT_NOT_FOUND, "error", null);
            else
                return new ProductResponseDto(Constants.GET_PRODUCT_BY_ID_SUCCESS, "success", product.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto addProduct(ProductRequestDto productDto) {
        try
        {
            if (productDto == null)
                return new ProductResponseDto(Constants.INVALID_NULL_INPUT, "error", null);

            // Check mandatory fields
            if (productDto.getName() == null || productDto.getName().isEmpty() || productDto.getName().matches(".*\\d.*"))
                return new ProductResponseDto(Constants.PRODUCT_NAME_NOT_VALID, "error", null);
            if (productDto.getQuantity() <= 0)
                return new ProductResponseDto(Constants.PRODUCT_QUANTITY_NOT_VALID, "error", null);
            if (productDto.getPrice() <= 0)
                return new ProductResponseDto(Constants.PRODUCT_PRICE_NOT_VALID, "error", null);

            // Check for existing product
            if (repository.findByName(productDto.getName()).isPresent())
                return new ProductResponseDto(Constants.PRODUCT_ALREADY_EXISTS, "error", null);

            // Create and populate product entity
            Product productToBeAdded = mapper.map(productDto, Product.class);
            productToBeAdded.setCreatedDate(new Date());

            // Add new product to db
            repository.save(productToBeAdded);
            return new ProductResponseDto(Constants.ADD_PRODUCT_SUCCESS, "success", productToBeAdded);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto updateProduct(int id, ProductRequestDto productDto) {
        try
        {
            // Check if product exists
            if (repository.findById(id).isEmpty())
                return new ProductResponseDto(Constants.PRODUCT_NOT_FOUND, "error", null);

            // Check mandatory fields
            if (productDto.getName() == null || productDto.getName().isEmpty() || productDto.getName().matches(".*\\d.*"))
                return new ProductResponseDto(Constants.PRODUCT_NAME_NOT_VALID, "error", null);
            if (productDto.getQuantity() <= 0)
                return new ProductResponseDto(Constants.PRODUCT_QUANTITY_NOT_VALID, "error", null);
            if (productDto.getPrice() <= 0)
                return new ProductResponseDto(Constants.PRODUCT_PRICE_NOT_VALID, "error", null);

            // Update product entity
            Product productToBeUpdated = mapper.map(productDto, Product.class);
            productToBeUpdated.setId(id);
            productToBeUpdated.setModifiedDate(new Date());

            // Add new product to db
            repository.save(productToBeUpdated);
            return new ProductResponseDto(Constants.UPDATE_PRODUCT_SUCCESS, "success", productToBeUpdated);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto deleteProduct(int id) {
        try {
            Optional<Product> product = repository.findById(id);

            if (product.isEmpty())
                return new ProductResponseDto(Constants.PRODUCT_NOT_FOUND, "error", null);
            else {
                repository.deleteById(id);
                return new ProductResponseDto(Constants.DELETE_PRODUCT_SUCCESS, "success", id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
