package com.example.store_management_tool;

import com.example.store_management_tool.data.ProductRequestDto;
import com.example.store_management_tool.data.entities.Product;
import com.example.store_management_tool.data.repositories.ProductRepository;
import com.example.store_management_tool.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void getAllProducts_withValidProductList_ShouldReturnList() {
        Product product1 = Product.builder()
                .id(1)
                .name("testProduct")
                .description("description")
                .price(20)
                .quantity(3)
                .build();

        Product product2 = Product.builder()
                .id(2)
                .name("testProduct2")
                .description("description2")
                .price(20)
                .quantity(3)
                .build();

        given(productRepository.findAll()).willReturn(List.of(product1, product2));
        ResponseEntity<List<Product>> response = productService.getAllProducts();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(product1, product2));
    }

    @Test
    void getProduct_withInvalidId_ShouldReturnError() {
        int invalidId = 0;

        ResponseEntity<Product> response = productService.getProductById(invalidId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getProduct_withValidId_ShouldReturnProduct() {
        Product product = Product.builder()
                .id(1)
                .name("testProduct")
                .description("description")
                .price(20)
                .quantity(3)
                .build();

        given(productRepository.findById(1)).willReturn(Optional.of(product));
        ResponseEntity<Product> response = productService.getProductById(product.getId());

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    void addProduct_withValidInput_ShouldReturnSuccess() {
        ProductRequestDto productToBeAdded = new ProductRequestDto("testName", "description", "testCategory", 10, 10);

        ResponseEntity<String> response = productService.addProduct(productToBeAdded);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Product added successfully");
    }

    @Test
    void addProduct_withInvalidInput_ShouldReturnError() {
        ProductRequestDto productWithInvalidName = new ProductRequestDto("testName2", "description", "testCategory", 10, 10);

        ResponseEntity<String> response = productService.addProduct(productWithInvalidName);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Product name should not contain any digit");
    }

    @Test
    void deleteProduct_withInvalidId_ShouldReturnError() {
        int invalidId = 0;

        ResponseEntity<String> response = productService.deleteProduct(invalidId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Invalid ID");
    }

    @Test
    void deleteProduct_withValidId_ShouldReturnSuccess() {
        int id = 10;

        ResponseEntity<String> response = productService.deleteProduct(id);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Product deleted successfully");
    }
}
