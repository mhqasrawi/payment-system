package com.progressoft.jip.payment.report.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.progressoft.jip.payment.report.core.AbstractReportGenerator;
import com.progressoft.jip.payment.report.core.ReportException;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportNode;
import com.progressoft.jip.payment.report.core.ReportSettings;

public class CSVReportGenerator extends AbstractReportGenerator implements ReportGenerator {
	private final String genericFailureMessage = "Failed while writing CSV report";
	private FileWriter writer;
	private final WriteOperation flushAndClose = () -> {
		this.writer.flush();
		this.writer.close();
	};

	public CSVReportGenerator() {
		this.supportedFileExtension = "csv";
	}

	@Override
	public String getSupportedFileExtension() {
		return this.supportedFileExtension;
	}

	@Override
	protected void startWrite() {
		write(() -> {
			this.writer = new FileWriter(new File(settings.getPath().
					resolve(this.settings.getFileName() + "." + this.supportedFileExtension).toString()));
			this.writer.append("Customer payments report\n");
		});
	}

	@Override
	protected void startPayment() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeNode(ReportNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endPayment() {

	}

	@Override
	protected void endWrite() {
		flushAndCloseWriter();
	}
	
	private void write(WriteOperation op) {
		super.writeAndHandleException(op, flushAndClose, genericFailureMessage);
	}

	private void flushAndCloseWriter() {
		super.writeAndHandleException(flushAndClose, WriteOperation.noOp, genericFailureMessage);
	}
}
