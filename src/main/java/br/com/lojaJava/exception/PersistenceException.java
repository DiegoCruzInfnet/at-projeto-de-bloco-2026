package br.com.lojaJava.exception;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
