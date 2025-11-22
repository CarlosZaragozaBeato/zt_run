package com.carloszaragoza.ztrun.domain.exception.login;

public class InvalidCredentialsException extends AuthException {
    public InvalidCredentialsException() {
        super("El usuario o la contraseña son incorrectos");
    }

}