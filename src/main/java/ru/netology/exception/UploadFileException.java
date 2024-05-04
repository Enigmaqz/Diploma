package ru.netology.exception;

public class UploadFileException extends RuntimeException {
    public UploadFileException() {
        super("Error: upload file");
    }
}
