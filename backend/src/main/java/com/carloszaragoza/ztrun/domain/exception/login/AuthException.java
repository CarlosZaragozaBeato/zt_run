package com.carloszaragoza.ztrun.domain.exception.login;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}