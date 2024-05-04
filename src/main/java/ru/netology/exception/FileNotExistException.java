package ru.netology.exception;

public class FileNotExistException extends RuntimeException{
    public FileNotExistException() {
        super("Error: file not exist");
    }
}