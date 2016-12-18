package com.progressoft.jip.payment.report.impl;

import java.util.Iterator;
import java.util.ServiceLoader;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.core.ReportManagerException;
import com.progressoft.jip.payment.report.core.ReportSettings;

public class ReportManagerImpl implements ReportManager {

	@Override
	public void generateReport(ReportSettings settings) throws ReportManagerException {
		ServiceLoader<ReportGenerator> loader = ServiceLoader.load(ReportGenerator.class);
		Iterator<ReportGenerator> it = loader.iterator();
		String extension = settings.getFileExtention();
		while (it.hasNext()) {
			ReportGenerator generator = it.next();
			if (generator.getSupportedFileExtension().equalsIgnoreCase(extension.toString())) {
				generator.generateReport(settings);
				return;
			}
		} 
		throw new ReportManagerException("Report file type " + settings.getFileExtention() + " is not supported");
	}
}
