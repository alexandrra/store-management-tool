package com.example.store_management_tool.data.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductWithTheSameNameAlreadyExistsException extends RuntimeException {
    public ProductWithTheSameNameAlreadyExistsException(String message) {
        super(message);
    }
}
