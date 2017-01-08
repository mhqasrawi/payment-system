package com.progressoft.jip.payment.transcription;

/**
 * Created by mhqasrawi on 08/12/16.
 */
public class LanguageNotSupportedException extends RuntimeException {
    public LanguageNotSupportedException() {
        super();
    }

    public LanguageNotSupportedException(String message) {
        super(message);
    }

    public LanguageNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LanguageNotSupportedException(Throwable cause) {
        super(cause);
    }

    protected LanguageNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
