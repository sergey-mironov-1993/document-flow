package org.example.exceptions;

public class DataEnteredIncorrectlyException extends RuntimeException {
    public DataEnteredIncorrectlyException() {
        super("Данные о контрагенте были введены неверно");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
