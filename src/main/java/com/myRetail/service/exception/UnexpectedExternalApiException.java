package com.myRetail.service.exception;

public class UnexpectedExternalApiException extends RuntimeException {
    public UnexpectedExternalApiException(String message) {
        super(message);
    }
}
