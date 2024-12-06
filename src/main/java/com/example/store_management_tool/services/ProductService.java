package com.example.store_management_tool.services;

import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.dtos.ProductResponseDto;
import com.example.store_management_tool.data.entities.Product;
import com.example.store_management_tool.data.exceptions.ProductWithTheSameNameAlreadyExistsException;
import com.example.store_management_tool.data.exceptions.ProductNotFoundException;
import com.example.store_management_tool.data.repositories.ProductRepository;
import com.example.store_management_tool.utils.Constants;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.mapper = modelMapper;
    }

    public ProductResponseDto getAllProducts(int pageNo, int pageSize, String sortBy, boolean ascending) {
        // Retrieve product list on pagination limits
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> fetchedProducts = repository.findAll(pageable);

        LOGGER.info(Constants.GET_ALL_PRODUCTS_SUCCESS);
        return new ProductResponseDto(Constants.GET_ALL_PRODUCTS_SUCCESS, "success", fetchedProducts);
    }

    public ProductResponseDto getProductById(int id) {
        Optional<Product> product = repository.findById(id);

        // Check if product was found
        if (product.isEmpty())
            throw new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND);

        LOGGER.info("{} {}", Constants.GET_PRODUCT_BY_ID_SUCCESS, id);
        return new ProductResponseDto(Constants.GET_PRODUCT_BY_ID_SUCCESS, "success", product.get());
    }

    public ProductResponseDto getProductsByCategory(String categoryName) {
        List<Product> products = repository.findByCategory(categoryName);

        LOGGER.info("{} {}", Constants.GET_PRODUCTS_BY_CATEGORY_SUCCESS, categoryName);
        return new ProductResponseDto(Constants.GET_PRODUCTS_BY_CATEGORY_SUCCESS, "success", products);
    }

    public ProductResponseDto addProduct(ProductRequestDto productDto) {
        // Check for existing product
        if (repository.findByName(productDto.getName()).isPresent())
            throw new ProductWithTheSameNameAlreadyExistsException(Constants.PRODUCT_WITH_SAME_NAME_ALREADY_EXISTS);

        // Create and populate product entity
        Product productToBeAdded = mapper.map(productDto, Product.class);
        productToBeAdded.setCreatedDate(new Date());

        // Add new product to db
        int id = repository.save(productToBeAdded).getId();

        LOGGER.info("{} {}", Constants.ADD_PRODUCT_SUCCESS, id);
        return new ProductResponseDto(Constants.ADD_PRODUCT_SUCCESS, "success", productToBeAdded);
    }

    public ProductResponseDto updateProduct(int id, ProductRequestDto productDto) {
        // Check if product exists
        if (repository.findById(id).isEmpty())
            throw new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND);

        // Update product entity
        Product productToBeUpdated = mapper.map(productDto, Product.class);
        productToBeUpdated.setId(id);
        productToBeUpdated.setModifiedDate(new Date());

        // Add new product to db
        repository.save(productToBeUpdated);

        LOGGER.info("{} {}", Constants.UPDATE_PRODUCT_SUCCESS, id);
        return new ProductResponseDto(Constants.UPDATE_PRODUCT_SUCCESS, "success", productToBeUpdated);
    }

    public ProductResponseDto deleteProduct(int id) {
        Optional<Product> product = repository.findById(id);

        // Check if product exists
        if (product.isEmpty())
            throw new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND);

        repository.deleteById(id);

        LOGGER.info("{} {}", Constants.DELETE_PRODUCT_SUCCESS, id);
        return new ProductResponseDto(Constants.DELETE_PRODUCT_SUCCESS, "success", id);
    }
}
