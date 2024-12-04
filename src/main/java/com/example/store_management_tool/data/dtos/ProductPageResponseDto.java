package com.example.store_management_tool.data.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPageResponseDto extends ProductResponseDto {
    private Pagination pagination;

    public ProductPageResponseDto(String message, String status, Object response, int currentPage, int pageSize, int totalSize) {
        super(message, status, response);
        this.pagination = new Pagination(currentPage, pageSize, totalSize);
    }
}
