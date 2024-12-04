package com.example.store_management_tool.data.exceptions;

import com.example.store_management_tool.controllers.ProductController;
import com.example.store_management_tool.data.dtos.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e) {
        LOGGER.error(e.getErrorMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(), e.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e) {
        LOGGER.error(e.getErrorMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(), e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        LOGGER.error(e.getErrorMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(), e.getErrorMessage()), HttpStatus.CONFLICT);
    }

    // Handling general exceptions
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(new Date(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
