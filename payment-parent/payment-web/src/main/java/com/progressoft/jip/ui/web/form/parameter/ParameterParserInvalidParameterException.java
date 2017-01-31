package com.progressoft.jip.ui.web.form.parameter;

public class ParameterParserInvalidParameterException extends RuntimeException {

    private static final long serialVersionUID = -4758728572250374598L;

    public ParameterParserInvalidParameterException() {
        super();
    }

    public ParameterParserInvalidParameterException(String message, Throwable cause, boolean enableSuppression,
                                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ParameterParserInvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterParserInvalidParameterException(String message) {
        super(message);
    }

    public ParameterParserInvalidParameterException(Throwable cause) {
        super(cause);
    }


}
