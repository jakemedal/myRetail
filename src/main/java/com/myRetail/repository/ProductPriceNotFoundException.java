package com.myRetail.repository;

public class ProductPriceNotFoundException extends RuntimeException {
    ProductPriceNotFoundException(String message) {
        super(message);
    }
}
