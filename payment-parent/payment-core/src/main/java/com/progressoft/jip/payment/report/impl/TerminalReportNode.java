package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.report.core.ReportNode;

public class TerminalReportNode implements ReportNode<String> {
    private String name;
    private String value;

    public TerminalReportNode(String name, String value) {
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
