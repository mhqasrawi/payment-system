package com.progressoft.jip.ui.xml;

/**
 * @author Ahmad.Jardat
 */
public class UserInterfaceParsingException extends RuntimeException {

    private static final long serialVersionUID = 1501096102549279382L;

    public UserInterfaceParsingException() {
        super();
    }

    public UserInterfaceParsingException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserInterfaceParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInterfaceParsingException(String message) {
        super(message);
    }

    public UserInterfaceParsingException(Throwable cause) {
        super(cause);
    }


}
