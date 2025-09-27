package com.sergio.technical_test.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException() { super(); }
}
