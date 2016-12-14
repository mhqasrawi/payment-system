package com.progressoft.jip.payment.report.core;

@SuppressWarnings("serial")
public class ReportException extends RuntimeException {

	public ReportException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReportException(String arg0) {
		super(arg0);
	}

	public ReportException(Throwable arg0) {
		super(arg0);
	}

}
