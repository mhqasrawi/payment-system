package com.progressoft.jip.currency.currenciesprovider;

/**
 * @author u623
 */
public class CurrencyException extends RuntimeException {

    public CurrencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyException(String message) {
        super(message);
    }

    public CurrencyException(Throwable cause) {
        super(cause);
    }

}
