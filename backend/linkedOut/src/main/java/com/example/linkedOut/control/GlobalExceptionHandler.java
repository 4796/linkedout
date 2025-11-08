package com.example.linkedOut.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.linkedOut.exception.AuthNotUniqueException;
import com.example.linkedOut.exception.AuthWrongPasswordException;

@ControllerAdvice 
public class GlobalExceptionHandler {

    // Handler za IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    	return new ResponseEntity<>("Invalid input: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
 
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle0(RuntimeException ex) {
    	return new ResponseEntity<>("Invalid input: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handler za NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
    	ex.printStackTrace();
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
 // password
    @ExceptionHandler(AuthWrongPasswordException.class)
    public ResponseEntity<String> handle1(IllegalArgumentException ex) {
    	return new ResponseEntity<>("Wrong input for password", HttpStatus.BAD_REQUEST);
    }
    
    // postoji korisnik
    @ExceptionHandler(AuthNotUniqueException.class)
    public ResponseEntity<String> handle2(IllegalArgumentException ex) {
    	return new ResponseEntity<>("Account already exists. ", HttpStatus.BAD_REQUEST);
    }

    //uslovi za korisnika
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    // Handler za bilo koji drugi izuzetak
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
    public ResponseEntity<String> handleGenericException(Exception ex) {
    	ex.printStackTrace();
    	return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	
    }
}
