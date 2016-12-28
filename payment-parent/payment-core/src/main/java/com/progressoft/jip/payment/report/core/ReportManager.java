package com.progressoft.jip.payment.report.core;

@FunctionalInterface
public interface ReportManager {

	void generateReport(ReportSettings settings);	
}
