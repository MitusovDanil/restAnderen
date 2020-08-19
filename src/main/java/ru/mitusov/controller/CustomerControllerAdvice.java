package ru.mitusov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mitusov.exception.CustomerNotFoundException;
import ru.mitusov.response.CustomerErrorResponse;

@RestControllerAdvice
public class CustomerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> customerExceptionHandler(CustomerNotFoundException exp) {
        CustomerErrorResponse errorResponse = new CustomerErrorResponse();
        errorResponse.setMessage(exp.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> customerExceptionHandler(Exception exp) {
        CustomerErrorResponse errorResponse = new CustomerErrorResponse();
        errorResponse.setMessage(exp.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
