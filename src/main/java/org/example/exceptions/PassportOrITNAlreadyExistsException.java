package org.example.exceptions;

public class PassportOrITNAlreadyExistsException extends RuntimeException{

    public PassportOrITNAlreadyExistsException() {
        super("Такой ИНН или паспортные данные уже существуют в системе.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
