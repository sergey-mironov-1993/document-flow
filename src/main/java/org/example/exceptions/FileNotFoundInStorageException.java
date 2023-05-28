package org.example.exceptions;

public class FileNotFoundInStorageException extends RuntimeException{
    public FileNotFoundInStorageException() {
        super("Файл не найден в хранилище!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
