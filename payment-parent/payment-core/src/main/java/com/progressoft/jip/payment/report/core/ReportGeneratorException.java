package com.progressoft.jip.payment.report.core;

@SuppressWarnings("serial")
public class ReportGeneratorException extends RuntimeException {

	public ReportGeneratorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReportGeneratorException(String arg0) {
		super(arg0);
	}

	public ReportGeneratorException(Throwable arg0) {
		super(arg0);
	}
}