package com.progressoft.jip.payment.report.core;

public interface ReportGenerator {
	
	public void generateReport(ReportSettings settings);
	
	public String getSupportedFileExtension();
}
