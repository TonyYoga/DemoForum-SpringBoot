package com.telran.springdiiocexceptionhandling.repository.exception;

public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(String message) {
        super(message);
    }

    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
