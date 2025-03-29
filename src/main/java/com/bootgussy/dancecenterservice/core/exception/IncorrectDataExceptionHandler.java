package com.bootgussy.dancecenterservice.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IncorrectDataExceptionHandler {
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<String> handleEventNotFoundException(IncorrectDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}