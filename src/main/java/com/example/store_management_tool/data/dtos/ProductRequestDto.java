package com.example.store_management_tool.data.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "Invalid product name: name is empty")
    @NotNull(message = "Invalid product name: NULL field")
    @Size(min = 3, max = 30, message = "Invalid product name: must be of 3 - 30 characters")
    private String name;
    private String description;
    @NotBlank(message = "Invalid product category: category is empty")
    @NotNull(message = "Invalid product category: NULL field")
    private String category;
    @Min(value = 1, message = "Invalid product price: must be greater than 0")
    private float price;
    @Min(value = 1, message = "Invalid product quantity: must be greater than 0")
    private int quantity;
}
