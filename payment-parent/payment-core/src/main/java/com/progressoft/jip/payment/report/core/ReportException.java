package com.progressoft.jip.payment.report.core;

/**
 * Created by u624 on 1/26/2017.
 */
public class ReportException extends Exception {
    public ReportException() {
        /* default constructor */
    }

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportException(Throwable cause) {
        super(cause);
    }

    public ReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
