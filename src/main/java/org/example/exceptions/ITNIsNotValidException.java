package org.example.exceptions;

public class ITNIsNotValidException extends RuntimeException {
    public ITNIsNotValidException() {
        super("Указанный ИНН недействителен!");
    }
}
