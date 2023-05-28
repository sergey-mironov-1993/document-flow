package org.example.exceptions;

public class FieldWithThisNameNotFoundException extends RuntimeException{
    public FieldWithThisNameNotFoundException(){
        super("Поля с таким названием не найдено в шаблоне!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
