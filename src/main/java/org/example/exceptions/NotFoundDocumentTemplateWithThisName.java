package org.example.exceptions;

public class NotFoundDocumentTemplateWithThisName extends RuntimeException{
    public NotFoundDocumentTemplateWithThisName() {
        super("Не существует такого шаблона документа!");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
