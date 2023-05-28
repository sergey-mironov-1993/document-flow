package org.example.exceptions;

public class EmptyDocumentListException extends RuntimeException{
    public EmptyDocumentListException() {
        super("Список документов пустой!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
