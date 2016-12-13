package com.progressoft.jip.payment.report;

import com.progressoft.jip.payment.report.ReportSettings.FileExtension;

public class ReportManager implements ReportGenerator {

	@Override
	public void generateReport(ReportSettings settings) {
		FileExtension extension = settings.getFileExtention();
		if (extension.equals(FileExtension.XML)) {
			new XMLReportGenerator().generateReport(settings);
		} else if (extension.equals(FileExtension.CSV)) {
			new CSVReportGenerator().generateReport(settings);
		} else {
			throw new IllegalArgumentException("File type " + extension.toString() + " is not supported");
		}
	}
}
