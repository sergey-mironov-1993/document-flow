package org.example.exceptions;

public class NotFoundUserWithThisTelegram extends RuntimeException{
    public NotFoundUserWithThisTelegram() {
        super("Пользователя с таким telegram-аккаунтом не существует!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
