package com.progressoft.jip.payment.report;

import java.util.LinkedList;

public class HierarchicalReportNode implements ReportNode<Iterable<ReportNode<String>>> {

	private Iterable<ReportNode<String>> values;
	private String name;

	public HierarchicalReportNode(String oRDERING_ACCOUNT_TAG, LinkedList<ReportNode<String>> subNodes) {
		name = oRDERING_ACCOUNT_TAG;
		values = subNodes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Iterable<ReportNode<String>> getValue() {
		return values;
	}

}
