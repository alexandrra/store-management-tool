package com.example.store_management_tool.data.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
