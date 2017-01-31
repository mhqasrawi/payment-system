package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.report.core.ReportException;
import com.progressoft.jip.payment.report.core.ReportNode;
import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import java.io.FileWriter;

public class XMLReportGenerator extends AbstractReportGenerator {
    private static final String GENERIC_FAILURE_MESSAGE = "Failed while writing XML report";
    private IndentingXMLStreamWriter xmlStreamWriter;
    private FileWriter fileWriter;
    private final WriteOperation flushAndClose = () -> {
        try {
            xmlStreamWriter.flush();
            xmlStreamWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            throw new ReportException(e);
        }
    };

    public XMLReportGenerator() {
        this.supportedFileExtension = "xml";
    }

    @Override
    protected void startWrite() {
        writeAndHandleException(() -> {
            try {
                XMLOutputFactory factory = XMLOutputFactory.newInstance();
                fileWriter = new FileWriter(
                        settingsSpi.getPath().resolve(settingsSpi.getFileName() + "." + this.supportedFileExtension).toString());
                xmlStreamWriter = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(fileWriter));
                xmlStreamWriter.writeStartDocument();
                xmlStreamWriter.writeStartElement(REPORT_NAME_TAG);
            } catch (Exception e) {
                throw new ReportException(e);
            }
        });
    }

    @Override
    protected void startPayment() {
        writeAndHandleException(() -> {
            try {
                xmlStreamWriter.writeStartElement(PAYMENT_TAG);
            } catch (Exception e) {
                throw new ReportException(e);
            }
        });
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void writeNode(ReportNode node) {
        Object value = node.getValue();
        writeAndHandleException(() -> {
            try {
                this.xmlStreamWriter.writeStartElement(node.getName());
                if (node instanceof HierarchicalReportNode) {
                    for (ReportNode reportNode : ((HierarchicalReportNode) node).getValue()) {
                        writeNode(reportNode);
                    }
                } else {
                    this.xmlStreamWriter.writeCharacters(String.valueOf(value));
                }
                this.xmlStreamWriter.writeEndElement();
            } catch (Exception e) {
                throw new ReportException(e);
            }
        });
    }

    @Override
    protected void endPayment() {
        writeAndHandleException(() -> {
            try {
                this.xmlStreamWriter.writeEndElement();
            } catch (Exception e) {
                throw new ReportException(e);
            }
        });
    }

    @Override
    public String getSupportedFileExtension() {
        return this.supportedFileExtension;
    }

    @Override
    protected void endWrite() {
        writeAndHandleException(() -> {
            try {
                this.xmlStreamWriter.writeEndElement();
                this.xmlStreamWriter.writeEndDocument();
                flushAndClose();
            } catch (Exception e) {
                throw new ReportException(e);
            }
        });
    }

    private void flushAndClose() {
        super.writeAndHandleException(flushAndClose, WriteOperation.DefaultWriteOperations.noOp, GENERIC_FAILURE_MESSAGE);
    }

    private void writeAndHandleException(WriteOperation op) {
        super.writeAndHandleException(op, flushAndClose, GENERIC_FAILURE_MESSAGE);
    }
}