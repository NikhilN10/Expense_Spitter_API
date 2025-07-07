package com.example.Expense_Splitter.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage(),
                "status", HttpStatus.NOT_FOUND.value()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", "Validation failed",
                "errors", errors,
                "status", HttpStatus.BAD_REQUEST.value()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>>handleGeneric(Exception ex){
        return new ResponseEntity<>(Map.of(
                "timestamp",LocalDateTime.now(),
                "message","Internal Server Error"+ex.getMessage(),
                "status",HttpStatus.INTERNAL_SERVER_ERROR.value()
        ),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can add more global handlers here
}

