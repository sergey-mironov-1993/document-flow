package org.example.exceptions;

public class FieldNotFillException extends RuntimeException {
    public FieldNotFillException() {
        super("Одно из полей не заполнено! Необходимо заполнить все поля полностью!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
