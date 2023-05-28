package org.example.exceptions;

public class NotFoundWithThisIdException extends RuntimeException {
    public NotFoundWithThisIdException() {
            super("Объекта с этим ID не существует!");
        }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
