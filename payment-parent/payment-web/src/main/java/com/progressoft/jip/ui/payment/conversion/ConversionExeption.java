package com.progressoft.jip.ui.payment.conversion;

/**
 * Created by u624 on 1/26/2017.
 */
public class ConversionExeption extends RuntimeException {
    public ConversionExeption() {
        /* default constructor */
    }

    public ConversionExeption(String message) {
        super(message);
    }

    public ConversionExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionExeption(Throwable cause) {
        super(cause);
    }

    public ConversionExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
