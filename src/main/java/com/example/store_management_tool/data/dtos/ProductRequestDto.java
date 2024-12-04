package com.example.store_management_tool.data.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private String category;
    private float price;
    private int quantity;
}
