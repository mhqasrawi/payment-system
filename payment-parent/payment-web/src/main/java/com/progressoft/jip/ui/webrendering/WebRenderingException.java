package com.progressoft.jip.ui.webrendering;

public class WebRenderingException extends RuntimeException {

    private static final long serialVersionUID = -5068610512532087572L;

    public WebRenderingException() {
        super();
    }

    public WebRenderingException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WebRenderingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebRenderingException(String message) {
        super(message);
    }

    public WebRenderingException(Throwable cause) {
        super(cause);
    }
}
