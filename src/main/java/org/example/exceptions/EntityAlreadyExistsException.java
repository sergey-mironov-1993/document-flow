package org.example.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException() {
        super("Уже существует объект с такими данными.");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
