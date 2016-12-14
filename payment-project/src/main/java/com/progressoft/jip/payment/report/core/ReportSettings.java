package com.progressoft.jip.payment.report.core;

import java.nio.file.Path;
import com.progressoft.jip.payment.PaymentDTO;

public class ReportSettings {
	private Iterable<PaymentDTO> payments;
	private Path path;
	private String fileName;
	private FileExtension fileExtension;

	public Iterable<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(Iterable<PaymentDTO> payments) {
		this.payments = payments;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileExtension getFileExtention() {
		return fileExtension;
	}

	public void setFileExtention(FileExtension fileExtention) {
		this.fileExtension = fileExtention;
	}
}