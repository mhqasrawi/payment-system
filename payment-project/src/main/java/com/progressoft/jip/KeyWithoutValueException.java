package com.progressoft.jip;

public class KeyWithoutValueException extends RuntimeException {

    private static final long serialVersionUID = -3500345534357807688L;

    public KeyWithoutValueException() {
	super();
    }

    public KeyWithoutValueException(String message, Throwable cause, boolean enableSuppression,
		    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KeyWithoutValueException(String message, Throwable cause) {
	super(message, cause);
    }

    public KeyWithoutValueException(String message) {
	super(message);
    }

    public KeyWithoutValueException(Throwable cause) {
	super(cause);
    }

}
