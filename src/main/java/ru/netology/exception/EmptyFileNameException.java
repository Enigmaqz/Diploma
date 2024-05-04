package ru.netology.exception;

public class EmptyFileNameException extends RuntimeException {
    public EmptyFileNameException() {
        super("Error: empty filename");
    }
}
