package com.myRetail.web;

public class NoProductPriceRequestBodyException extends RuntimeException {
    NoProductPriceRequestBodyException(String message) {
        super(message);
    }
}
