package com.progressoft.jip.payment.report.core;

public interface ReportGenerator {

    public void generateReport(ReportSettingsSpi settings);

    public String getSupportedFileExtension();
}
