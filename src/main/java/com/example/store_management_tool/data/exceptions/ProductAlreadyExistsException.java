package com.example.store_management_tool.data.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductAlreadyExistsException extends RuntimeException {
    private String errorMessage;
}
