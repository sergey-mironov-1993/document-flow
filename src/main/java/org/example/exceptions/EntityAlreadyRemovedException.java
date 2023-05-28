package org.example.exceptions;

public class EntityAlreadyRemovedException extends RuntimeException{
    public EntityAlreadyRemovedException() {
        super("Данный объект уже удалён!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
