package com.example.ms_gateway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarNoEncontrado(IllegalArgumentException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarError(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
