package com.example.store_management_tool.data.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pagination {
    private int currentPage;
    private int pageSize;
    private int totalSize;
}
