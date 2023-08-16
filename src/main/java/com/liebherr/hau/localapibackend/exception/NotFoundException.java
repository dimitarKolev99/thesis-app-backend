package com.liebherr.hau.localapibackend.exception;

public class NotFoundException extends RuntimeException{

    private final Class<?> notFoundType;

    public NotFoundException(String message, Class<?> notFoundType) {
        super(message);
        this.notFoundType = notFoundType;
    }

    public Class<?> getNotFoundType() {
        return notFoundType;
    }

}
