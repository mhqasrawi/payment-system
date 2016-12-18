package com.progressoft.jip.payment.report.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import com.progressoft.jip.payment.report.core.AbstractReportGenerator;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportNode;

@SuppressWarnings("rawtypes")
public class CSVReportGenerator extends AbstractReportGenerator implements ReportGenerator {
	private static final String VALUE_SEPARATOR = ",";
	private static final String NESTED_NODE_START_DELIMITER = "(";
	private static final String NESTED_NODE_END_DELIMITER = ")";
	private final String genericFailureMessage = "Failed while writing CSV report";
	private boolean nodeStarted = true;
	private int actionOnFirstRecord = 0;
	private int actionAfterFirstRecord = 1;
	private int divergenceCounter = actionOnFirstRecord;
	private FileWriter writer;
	private LinkedList<ReportNode> tempNodeCache = new LinkedList<>();
	private CSVWriteActionHandler[] writeNodeActions = new CSVWriteActionHandler[2];
	private CSVWriteActionHandler[] endPaymentActions = new CSVWriteActionHandler[2];
	private final WriteOperation flushAndClose = () -> {
		this.writer.flush();
		this.writer.close();
	};

	public CSVReportGenerator() {
		this.supportedFileExtension = "csv";
		initActions();
	}

	@Override
	protected void startWrite() {
		executeAndHandleException(() -> {
			this.writer = new FileWriter(new File(settings.getPath()
					.resolve(this.settings.getFileName() + "." + this.supportedFileExtension).toString()));
		});
	}

	@Override
	protected void startPayment() {
		nodeStarted = true;
	}

	@Override
	protected void writeNode(ReportNode node) {
		writeNodeActions[divergenceCounter].handleAction(node);
	}

	@Override
	protected void endPayment() {
		endPaymentActions[divergenceCounter].handleAction(null);
	}

	@Override
	protected void endWrite() {
		flushAndCloseWriter();
	}
	
	@Override
	public String getSupportedFileExtension() {
		return this.supportedFileExtension;
	}

	private void initActions() {
		writeNodeActions[actionOnFirstRecord] = (node) -> writeColumns(node, false);
		writeNodeActions[actionAfterFirstRecord] = (node) -> writeRecordNode(node);
		endPaymentActions[actionOnFirstRecord] = (node) -> {
			newLine();
			nodeStarted = true;
			divergenceCounter = actionAfterFirstRecord;
			while (!tempNodeCache.isEmpty()) {
				writeRecordNode(tempNodeCache.poll());
			}
			newLine();
		};
		endPaymentActions[actionAfterFirstRecord] = (node) -> newLine();
	}

	private void writeColumns(ReportNode node, boolean nested) {
		applySeparator();
		put(node.getName());
		nodeStarted = false;
		if (!nested) {
			tempNodeCache.add(node);
		}
		if (node instanceof HierarchicalReportNode) {
			surroundNodeWithDelimsRecursively((HierarchicalReportNode) node, (newNode) -> writeColumns(newNode, true));
		} 
	}

	private void writeRecordNode(ReportNode node) {
		applySeparator();
		if (node instanceof HierarchicalReportNode) {
			surroundNodeWithDelimsRecursively((HierarchicalReportNode) node, (newNode) -> writeRecordNode(newNode));
		} else {
			put(String.valueOf(node.getValue()));
			nodeStarted = false;
		}
	}

	private void surroundNodeWithDelimsRecursively(HierarchicalReportNode node, CSVWriteActionHandler recursiveHandler) {
		nodeStarted = true;
		put(NESTED_NODE_START_DELIMITER);
		for (ReportNode n : node.getValue()) {
			recursiveHandler.handleAction(n);
		}
		put(NESTED_NODE_END_DELIMITER);
	}

	private void applySeparator() {
		if (!nodeStarted) {
			put(VALUE_SEPARATOR);
		}
	}

	private void newLine() {
		put("\n");
	}

	private void put(String field) {
		executeAndHandleException(() -> this.writer.append(field));
	}

	private void executeAndHandleException(WriteOperation op) {
		super.writeAndHandleException(op, flushAndClose, genericFailureMessage);
	}

	private void flushAndCloseWriter() {
		super.writeAndHandleException(flushAndClose, WriteOperation.noOp, genericFailureMessage);
	}

	private interface CSVWriteActionHandler {
		void handleAction(ReportNode node);
	}
}