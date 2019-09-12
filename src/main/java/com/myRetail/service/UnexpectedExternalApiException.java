package com.myRetail.service;

public class UnexpectedExternalApiException extends RuntimeException {
    UnexpectedExternalApiException(String message) {
        super(message);
    }
}
