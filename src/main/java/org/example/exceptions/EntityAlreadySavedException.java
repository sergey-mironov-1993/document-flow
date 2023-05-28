package org.example.exceptions;

public class EntityAlreadySavedException extends RuntimeException{
    public EntityAlreadySavedException() {
        super("Такая сущность уже существует в системе!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
