package com.progressoft.jip.payment.usecase;

/**
 * Created by u612 on 1/25/2017.
 */
public class UseCaseExecutionException extends RuntimeException {

    public UseCaseExecutionException() {
    }

    public UseCaseExecutionException(String message) {
        super(message);
    }

    public UseCaseExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UseCaseExecutionException(Throwable cause) {
        super(cause);
    }

    public UseCaseExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
