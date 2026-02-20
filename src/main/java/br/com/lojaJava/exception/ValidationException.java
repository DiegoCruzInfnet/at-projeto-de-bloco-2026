package br.com.lojaJava.exception;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
