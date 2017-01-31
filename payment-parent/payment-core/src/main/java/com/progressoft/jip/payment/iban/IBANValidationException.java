package com.progressoft.jip.payment.iban;

public class IBANValidationException extends RuntimeException {

    private static final long serialVersionUID = 8101098478527213446L;

    public IBANValidationException() {
        super();
    }

    public IBANValidationException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IBANValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IBANValidationException(String message) {
        super(message);
    }

    public IBANValidationException(Throwable cause) {
        super(cause);
    }

}
