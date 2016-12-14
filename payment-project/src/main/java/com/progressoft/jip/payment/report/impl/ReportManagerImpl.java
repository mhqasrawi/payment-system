package com.progressoft.jip.payment.report.impl;

import java.util.Iterator;
import java.util.ServiceLoader;
import com.progressoft.jip.payment.report.core.FileExtension;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportException;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.core.ReportSettings;

public class ReportManagerImpl implements ReportManager {

	@Override
	public void generateReport(ReportSettings settings) {
		ServiceLoader<ReportGenerator> loader = ServiceLoader.load(ReportGenerator.class);
		Iterator<ReportGenerator> it = loader.iterator();
		FileExtension extension = settings.getFileExtention();
		while(it.hasNext()) {
			ReportGenerator generator = it.next();
			if(generator.getSupportedFileExtension().equalsIgnoreCase(extension.toString())) {
				generator.generateReport(settings);
				return;
			}
		}
		throw new ReportException("Report file type " + extension.toString() + " is not supported");
	}
}
