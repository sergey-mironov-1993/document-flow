package org.example.exceptions;

public class ITNNotFoundException extends RuntimeException {
    public ITNNotFoundException() {
        super("Информация об ИНН не найдена. Рекомендуем проверить правильность введённых данных и повторить попытку поиска.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
