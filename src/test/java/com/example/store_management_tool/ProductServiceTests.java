package com.example.store_management_tool;

import com.example.store_management_tool.data.dtos.ProductRequestDto;
import com.example.store_management_tool.data.dtos.ProductResponseDto;
import com.example.store_management_tool.data.entities.Product;
import com.example.store_management_tool.data.exceptions.ProductNotFoundException;
import com.example.store_management_tool.data.exceptions.ProductWithTheSameNameAlreadyExistsException;
import com.example.store_management_tool.data.repositories.ProductRepository;
import com.example.store_management_tool.services.ProductService;
import com.example.store_management_tool.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void getAllProducts_withValidProductList_ShouldReturnList() {
        Product product1 = new Product(1, "testProduct1", "description", "category", 20, 10, new Date(), new Date());
        Product product2 = new Product(1, "testProduct2", "description", "category", 20, 10, new Date(), new Date());
        int pageNo = 0;
        int pageSize = 5;
        String sortBy = "id";
        boolean ascending = true;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());

        Page<Product> products = new PageImpl<>(List.of(product1, product2), pageable, 2);
        given(productRepository.findAll(pageable)).willReturn(products);
        ProductResponseDto response = productService.getAllProducts(pageNo, pageSize, sortBy, ascending);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.GET_ALL_PRODUCTS_SUCCESS);
        assertThat(response.getResponse()).isEqualTo(products);
    }

    @Test
    void getProductsByCategory_withValidCategory_ShouldReturnList() {
        String categoryName = "category";
        Product product1 = new Product(1, "testProduct1", "description", "category", 20, 10, new Date(), new Date());
        Product product2 = new Product(1, "testProduct2", "description", "category", 20, 10, new Date(), new Date());
        List<Product> products = List.of(product1, product2);

        given(productRepository.findByCategory(categoryName)).willReturn(products);
        ProductResponseDto response = productService.getProductsByCategory(categoryName);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.GET_PRODUCTS_BY_CATEGORY_SUCCESS);
        assertThat(response.getResponse()).isEqualTo(products);
    }

    @Test
    void getProduct_notInRepository_ShouldThrowProductNotFound() {
        int id = 10;
        given(productRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void getProduct_withValidId_ShouldReturnProduct() {
        Product product = new Product(1, "testProduct", "description", "category", 20, 10, new Date(), new Date());

        given(productRepository.findById(1)).willReturn(Optional.of(product));
        ProductResponseDto response = productService.getProductById(product.getId());

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.GET_PRODUCT_BY_ID_SUCCESS);
        assertThat(response.getResponse()).isEqualTo(product);
    }

    @Test
    void addProduct_withValidInput_ShouldReturnSuccess() {
        ProductRequestDto productToBeAdded = new ProductRequestDto("testName", "description", "testCategory", 10, 10);

        given(productRepository.findByName(productToBeAdded.getName())).willReturn(Optional.empty());
        given(productRepository.save(any())).willReturn(new Product());
        ProductResponseDto response = productService.addProduct(productToBeAdded);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.ADD_PRODUCT_SUCCESS);
    }

    @Test
    void addProduct_alreadyInRepo_ShouldThrowError() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test");
        given(productRepository.findByName("Test")).willReturn(Optional.of(new Product()));
        assertThrows(ProductWithTheSameNameAlreadyExistsException.class, () -> productService.addProduct(productRequestDto));
    }

    @Test
    void updateProduct_withValidInput_ShouldReturnSuccess() {
        int id = 10;
        ProductRequestDto productDto = new ProductRequestDto("name", "description", "testCategory", 10, 10);
        Product productEntity = new Product(id, "testName", "description", "category", 20, 10, new Date(), new Date());

        given(productRepository.findById(id)).willReturn(Optional.of(productEntity));
        ProductResponseDto response = productService.updateProduct(id, productDto);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.UPDATE_PRODUCT_SUCCESS);
    }

    @Test
    void updateProduct_alreadyInRepo_ShouldThrowError() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test");

        given(productRepository.findByName("Test")).willReturn(Optional.of(new Product()));
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(anyInt(), productRequestDto));
    }

    @Test
    void deleteProduct_notInRepository_ShouldThrowProductNotFound() {
        int id = 10;
        given(productRepository.findById(id)).willReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(id));
    }

    @Test
    void deleteProduct_withValidId_ShouldReturnSuccess() {
        int id = 10;
        Product productEntity = new Product(id, "testName", "description", "category", 20, 10, new Date(), new Date());
        given(productRepository.findById(id)).willReturn(Optional.of(productEntity));
        ProductResponseDto response = productService.deleteProduct(id);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getMessage()).isEqualTo(Constants.DELETE_PRODUCT_SUCCESS);
        assertThat(response.getResponse()).isEqualTo(id);
    }
}
