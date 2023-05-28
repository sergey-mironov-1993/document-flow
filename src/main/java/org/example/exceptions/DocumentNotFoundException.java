package org.example.exceptions;

public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException() {
        super("Документ с таким id не найден!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
