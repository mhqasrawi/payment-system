package com.progressoft.jip.payment.report.impl;

import java.nio.file.Path;
import org.slf4j.LoggerFactory;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportSettingsException;
import com.progressoft.jip.payment.report.core.ReportSettingsSpi;
import com.progressoft.jip.payment.transcription.Transcription;

public class ReportSettingsImpl implements ReportSettingsSpi {
	private Iterable<PaymentDTO> payments;
	private Path path;
	private String fileName;
	private String fileExtension;
	private Class<? extends Transcription> transcriber;

	@Override
	public Iterable<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(Iterable<PaymentDTO> payments) {
		this.payments = payments;
	}

	@Override
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getFileExtention() {
		return fileExtension;
	}

	public void setFileExtention(String fileExtention) {
		this.fileExtension = fileExtention;
	}
	
	public void setTranscriberClass(Class<? extends Transcription> transcriberClass) {
		this.transcriber = transcriberClass;
	}

	@Override
	public Transcription newTranscriberInstance() {
		try {
			return this.transcriber.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			String message = "Failed to initialize transcriber \"" + this.transcriber.getSimpleName() + "\""; 
			LoggerFactory.getLogger(AbstractReportGenerator.class).error(message, e);
			throw new ReportSettingsException(message, e);
		}
	}
}