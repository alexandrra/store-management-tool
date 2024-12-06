package com.example.store_management_tool.data.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
