package ru.raperan.poopoo.exception;

public class MinioOperationException extends RuntimeException {

    public MinioOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
