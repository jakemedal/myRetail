package com.myRetail.service;

public class ProductTitleNotFoundException extends RuntimeException {
    ProductTitleNotFoundException(String message) {
        super(message);
    }
}
