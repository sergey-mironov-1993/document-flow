package org.example.exceptions;

public class NotSelfEmploymentOrIndividualException extends RuntimeException {
    public NotSelfEmploymentOrIndividualException() {
        super("Не является самозанятым или ИП");
    }
}
