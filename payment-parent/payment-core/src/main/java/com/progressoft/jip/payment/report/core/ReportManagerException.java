package com.progressoft.jip.payment.report.core;

@SuppressWarnings("serial")
public class ReportManagerException extends RuntimeException {

    public ReportManagerException() {
        super();
    }

    public ReportManagerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public ReportManagerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ReportManagerException(String arg0) {
        super(arg0);
    }

    public ReportManagerException(Throwable arg0) {
        super(arg0);
    }
}