package com.myRetail.web.exception;

public class NoProductPriceRequestBodyException extends RuntimeException {
    public NoProductPriceRequestBodyException(String message) {
        super(message);
    }
}
