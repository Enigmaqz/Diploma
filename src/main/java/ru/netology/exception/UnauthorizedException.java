package ru.netology.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Error: unauthorized user");
    }
}