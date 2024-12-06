package com.example.store_management_tool.data.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors (MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleProductNotFoundException(ProductNotFoundException e) {
        List<String> errors = Collections.singletonList(e.getMessage());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductWithTheSameNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleProductWithSameNameAlreadyExistsException(ProductWithTheSameNameAlreadyExistsException e) {
        List<String> errors = Collections.singletonList(e.getMessage());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.CONFLICT);    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotFound(ProductWithTheSameNameAlreadyExistsException e) {
        List<String> errors = Collections.singletonList(e.getMessage());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // Handling general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleException(Exception e) {
        List<String> errors = Collections.singletonList(e.getMessage());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException e) {
        List<String> errors = Collections.singletonList(e.getMessage());

        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
