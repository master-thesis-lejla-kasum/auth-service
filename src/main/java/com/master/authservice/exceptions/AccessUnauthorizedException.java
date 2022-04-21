package com.master.authservice.exceptions;

public class AccessUnauthorizedException extends RuntimeException {
    public AccessUnauthorizedException() {
        super();
    }

    public AccessUnauthorizedException(String message) {
        super(message);
    }
}
