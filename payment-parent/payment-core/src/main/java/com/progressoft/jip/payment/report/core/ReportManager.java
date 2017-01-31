package com.progressoft.jip.payment.report.core;

@FunctionalInterface
public interface ReportManager {

    void generateReport(ReportSettingsSpi settings);
}
