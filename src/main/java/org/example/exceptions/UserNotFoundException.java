package org.example.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователь с таким логином или паролем не найден! Проверьте правильность введенных данных.");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
