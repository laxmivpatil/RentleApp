package com.techverse.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.techverse.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex) {
        // Create a custom error response
    	 System.out.println(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(false, "user not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
     @ExceptionHandler(IOException.class)
     public ResponseEntity<Object> handleIO(IOException ex) {
         // Create a custom error response
     	 System.out.println(ex.getMessage());
         ErrorResponse errorResponse = new ErrorResponse(false, "file storage error");
         return new ResponseEntity<>(errorResponse, HttpStatus.OK);
     }
     
     
     
     
     @ExceptionHandler(UsernameNotFoundException.class)
     public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
         // Create a custom error response
         ErrorResponse errorResponse = new ErrorResponse(false, ex.getMessage());
         return new ResponseEntity<>(errorResponse, HttpStatus.OK);
     }
     @ExceptionHandler(BadCredentialsException.class)
     public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
         // Create a custom error response
    	 System.out.println(ex);
         ErrorResponse errorResponse = new ErrorResponse(false, ex.getMessage());
         return new ResponseEntity<>(errorResponse, HttpStatus.OK);
     }
    // Add more @ExceptionHandler methods for other custom exceptions

    // Generic exception handler
   /* @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Create a generic error response
        ErrorResponse errorResponse = new ErrorResponse(false, "Internal Server Error123");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}
