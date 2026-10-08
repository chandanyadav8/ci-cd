package com.example.geo_tracking_service.exception;

import com.example.geo_tracking_service.dto.ErrorResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponses> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ErrorResponses response = new ErrorResponses(
                LocalDateTime.now(),
                400,
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponses> handleResourceNotFound(
            ResourceNotFoundException exception) {

        ErrorResponses errorResponses = new ErrorResponses(
                LocalDateTime.now(),
                404,
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(404).body(errorResponses);
    }
}