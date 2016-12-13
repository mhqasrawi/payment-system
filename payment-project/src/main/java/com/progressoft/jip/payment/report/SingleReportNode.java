package com.progressoft.jip.payment.report;

public class SingleReportNode implements ReportNode<String> {
	private String name;
	private String value;

	public SingleReportNode(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	
}
