package com.example.store_management_tool.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private Date timestamp;
    private String message;
}
