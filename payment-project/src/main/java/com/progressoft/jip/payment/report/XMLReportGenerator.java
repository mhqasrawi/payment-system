package com.progressoft.jip.payment.report;

import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javanet.staxutils.IndentingXMLStreamWriter;

public class XMLReportGenerator extends AbstractReportGenerator {
	private final String genericFailureMessage = "Failed while writing XML report";
	private IndentingXMLStreamWriter xmlStreamWriter;
	private FileWriter fileWriter;

	@Override
	protected void startWrite() {
		try {
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			fileWriter = new FileWriter(settings.getPath()
					.resolve(settings.getFileName() + "." + settings.getFileExtention().toString().toLowerCase())
					.toString());
			xmlStreamWriter = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(fileWriter));
			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement(this.REPORT_NAME_TAG);
		} catch (IOException | XMLStreamException e) {
			throw new ReportGeneratorException(genericFailureMessage, e);
		}
	}

	protected void endWrite() {
		try {
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.flush();
			xmlStreamWriter.close();
			fileWriter.close();
		} catch (XMLStreamException | IOException e) {
			throw new ReportGeneratorException(genericFailureMessage, e);
		}
	}

	@Override
	protected void startPayment() {
		try {
			xmlStreamWriter.writeStartElement("payment");
		} catch (XMLStreamException e) {
			throw new ReportGeneratorException(genericFailureMessage, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void writeNode(ReportNode node) {
		Object value = node.getValue();
		try {
			xmlStreamWriter.writeStartElement(node.getName());
			if (node instanceof HierarchicalReportNode) {
				for (ReportNode reportNode : ((HierarchicalReportNode) node).getValue()) {
					writeNode(reportNode);
				}
			} else {
				xmlStreamWriter.writeCharacters(String.valueOf(value));
			}
			xmlStreamWriter.writeEndElement();
		} catch (XMLStreamException e) {
			throw new ReportGeneratorException(genericFailureMessage, e);
		}
	}

	@Override
	protected void endPayment() {
		try {
			xmlStreamWriter.writeEndElement();
		} catch (XMLStreamException e) {
			throw new ReportGeneratorException(genericFailureMessage, e);
		}
	}
}