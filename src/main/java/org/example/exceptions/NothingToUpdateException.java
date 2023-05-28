package org.example.exceptions;

public class NothingToUpdateException extends RuntimeException{
    public NothingToUpdateException() {
        super("Нет данных для внесения изменений.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
