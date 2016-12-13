package com.progressoft.jip.payment.report;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.progressoft.jip.payment.PaymentDTO;

public class ReportSettings {
	private Iterable<PaymentDTO> payments;
	private Path path;
	private String fileName;
	private FileExtension fileExtension;

	public ReportSettings(Iterable<PaymentDTO> payments, String path, String fileName, FileExtension fileExtension) {
		super();
		this.payments = payments;
		this.path = Paths.get(path);
		this.fileName = fileName;
		this.fileExtension = fileExtension;
	}

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

	public enum FileExtension {
		CSV, XML
	}
}
