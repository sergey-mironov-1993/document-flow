package org.example.exceptions;

public class EmptyEntityDataException extends RuntimeException{

    public EmptyEntityDataException() {
        super("Получены не все данные для обновления сущности.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
