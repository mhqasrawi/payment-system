package com.progressoft.jip.payment.report.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.core.ReportManagerException;
import com.progressoft.jip.payment.report.core.ReportSettings;

public class ReportManagerImpl implements ReportManager {
	private HashMap<String, Class<? extends ReportGenerator>> generators = new HashMap<>();

	@Override
	public void generateReport(ReportSettings settings) {
		Class<? extends ReportGenerator> generator;
		if (generators.isEmpty()) {
			loadGenerators();
		}
		if ((generator = generators.get(settings.getFileExtention())) != null) {
			try {
				generator.newInstance().generateReport(settings);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new ReportManagerException("Failed while generating report", e);
			}
		} else {
			throw new ReportManagerException("Report file type " + settings.getFileExtention() + " is not supported");
		}
	}

	private void loadGenerators() {
		ServiceLoader<ReportGenerator> loader = ServiceLoader.load(ReportGenerator.class);
		Iterator<ReportGenerator> it = loader.iterator();
		while (it.hasNext()) {
			ReportGenerator next = it.next();
			generators.put(next.getSupportedFileExtension(), next.getClass());
		}
	}
}