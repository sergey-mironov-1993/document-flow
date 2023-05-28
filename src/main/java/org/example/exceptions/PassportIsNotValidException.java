package org.example.exceptions;

public class PassportIsNotValidException extends RuntimeException {

    public PassportIsNotValidException() {
        super("Паспорт не действителен!");
    }

    public PassportIsNotValidException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
