package org.example.exceptions;

public class DocumentAlreadySavedException extends RuntimeException{
    public DocumentAlreadySavedException() {
        super("Документ с таким номером уже существует! Измените номер нового документа.");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
