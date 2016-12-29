package com.progressoft.jip.payment.report.impl;

import java.io.FileWriter;
import javax.xml.stream.XMLOutputFactory;
import com.progressoft.jip.payment.report.core.AbstractReportGenerator;
import com.progressoft.jip.payment.report.core.ReportNode;
import javanet.staxutils.IndentingXMLStreamWriter;

public class XMLReportGenerator extends AbstractReportGenerator {
	private static final String GENERIC_FAILURE_MESSAGE = "Failed while writing XML report";
	private IndentingXMLStreamWriter xmlStreamWriter;
	private FileWriter fileWriter;
	private final WriteOperation flushAndClose = () -> {
		xmlStreamWriter.flush();
		xmlStreamWriter.close();
		fileWriter.close();
	};

	public XMLReportGenerator() {
		this.supportedFileExtension = "xml";
	}

	@Override
	protected void startWrite() {
		writeAndHandleException(() -> {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			fileWriter = new FileWriter(
					settings.getPath().resolve(settings.getFileName() + "." + this.supportedFileExtension).toString());
			xmlStreamWriter = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(fileWriter));
			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement(REPORT_NAME_TAG);
		});
	}
	
	@Override
	protected void startPayment() {
		writeAndHandleException(() -> xmlStreamWriter.writeStartElement(PAYMENT_TAG));
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected void writeNode(ReportNode node) {
		Object value = node.getValue();
		writeAndHandleException(() -> {
			this.xmlStreamWriter.writeStartElement(node.getName());
			if (node instanceof HierarchicalReportNode) {
				for (ReportNode reportNode : ((HierarchicalReportNode) node).getValue()) {
					writeNode(reportNode);
				}
			} else {
				this.xmlStreamWriter.writeCharacters(String.valueOf(value));
			}
			this.xmlStreamWriter.writeEndElement();
		});
	}

	@Override
	protected void endPayment() {
		writeAndHandleException(this.xmlStreamWriter::writeEndElement);
	}

	@Override
	public String getSupportedFileExtension() {
		return this.supportedFileExtension;
	}

	@Override
	protected void endWrite() {
		writeAndHandleException(() -> {
			this.xmlStreamWriter.writeEndElement();
			this.xmlStreamWriter.writeEndDocument();
			flushAndClose();
		});
	}

	private void flushAndClose() {
		super.writeAndHandleException(flushAndClose, WriteOperation.noOp, GENERIC_FAILURE_MESSAGE);
	}

	private void writeAndHandleException(WriteOperation op) {
		super.writeAndHandleException(op, flushAndClose, GENERIC_FAILURE_MESSAGE);
	}
}