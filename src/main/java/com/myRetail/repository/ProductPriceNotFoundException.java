package com.myRetail.repository;

class ProductPriceNotFoundException extends RuntimeException {
    ProductPriceNotFoundException(String message) {
        super(message);
    }
}
