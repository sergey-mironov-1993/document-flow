package org.example.exceptions;

public class NotFoundObjectWithThisNameException extends RuntimeException {
    public NotFoundObjectWithThisNameException() {
        super("Объекта с таким наименованием не существует! Проверьте правильность введенных данных.");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
