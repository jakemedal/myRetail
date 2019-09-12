package com.myRetail.service.exception;

public class ProductTitleNotFoundException extends RuntimeException {
    public ProductTitleNotFoundException(String message) {
        super(message);
    }
}
