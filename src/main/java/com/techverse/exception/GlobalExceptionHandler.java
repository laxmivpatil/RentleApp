package com.techverse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.techverse.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex) {
        // Create a custom error response
        ErrorResponse errorResponse = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

     
     
     
     
     @ExceptionHandler(UsernameNotFoundException.class)
     public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
         // Create a custom error response
         ErrorResponse errorResponse = new ErrorResponse(false, ex.getMessage());
         return new ResponseEntity<>(errorResponse, HttpStatus.OK);
     }
    // Add more @ExceptionHandler methods for other custom exceptions

    // Generic exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Create a generic error response
        ErrorResponse errorResponse = new ErrorResponse(false, "Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
