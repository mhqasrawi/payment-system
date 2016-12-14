package com.progressoft.jip.payment.report.impl;

import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import com.progressoft.jip.payment.report.core.AbstractReportGenerator;
import com.progressoft.jip.payment.report.core.ReportNode;
import javanet.staxutils.IndentingXMLStreamWriter;

public class XMLReportGenerator extends AbstractReportGenerator {
	private final String genericFailureMessage = "Failed while writing XML report";
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
		write(() -> {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			fileWriter = new FileWriter(
					settings.getPath().resolve(settings.getFileName() + "." + this.supportedFileExtension).toString());
			xmlStreamWriter = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(fileWriter));
			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement(this.REPORT_NAME_TAG);
		});
	}

	protected void endWrite() {
		write(() -> {
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();
			flushAndClose();
		});
	}

	private void flushAndClose() throws XMLStreamException, IOException {
		super.writeAndHandleException(flushAndClose, WriteOperation.noOp, genericFailureMessage);
	}

	@Override
	protected void startPayment() {
		write(() -> xmlStreamWriter.writeStartElement(this.PAYMENT_TAG));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void writeNode(ReportNode node) {
		Object value = node.getValue();
		write(() -> {
			xmlStreamWriter.writeStartElement(node.getName());
			if (node instanceof HierarchicalReportNode) {
				for (ReportNode reportNode : ((HierarchicalReportNode) node).getValue()) {
					writeNode(reportNode);
				}
			} else {
				xmlStreamWriter.writeCharacters(String.valueOf(value));
			}
			xmlStreamWriter.writeEndElement();
		});
	}

	@Override
	protected void endPayment() {
		write(() -> xmlStreamWriter.writeEndElement());
	}

	private void write(WriteOperation op) {
		super.writeAndHandleException(op, flushAndClose, genericFailureMessage);
	}

	@Override
	public String getSupportedFileExtension() {
		return supportedFileExtension;
	}
}