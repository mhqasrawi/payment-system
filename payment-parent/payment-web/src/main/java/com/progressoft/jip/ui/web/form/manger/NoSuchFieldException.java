package com.progressoft.jip.ui.web.form.manger;

public class NoSuchFieldException extends RuntimeException {

    private static final long serialVersionUID = -5011783183555179754L;

    public NoSuchFieldException() {
        super();
    }

    public NoSuchFieldException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoSuchFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFieldException(String message) {
        super(message);
    }

    public NoSuchFieldException(Throwable cause) {
        super(cause);
    }


}
