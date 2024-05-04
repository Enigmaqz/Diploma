package ru.netology.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("Error: bad credentials");
    }
}