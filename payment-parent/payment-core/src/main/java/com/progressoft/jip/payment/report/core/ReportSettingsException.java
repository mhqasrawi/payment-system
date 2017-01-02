package com.progressoft.jip.payment.report.core;

@SuppressWarnings("serial")
public class ReportSettingsException extends RuntimeException {

	public ReportSettingsException() {
		super();
	}

	public ReportSettingsException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ReportSettingsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReportSettingsException(String arg0) {
		super(arg0);
	}

	public ReportSettingsException(Throwable arg0) {
		super(arg0);
	}

	
}
