package org.example.exceptions;

public class IncorrectITNException extends RuntimeException {
    public IncorrectITNException() {
        super("Введен некорректный ИНН контрагента!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
