package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.report.core.ReportNode;

@SuppressWarnings("rawtypes")
public class HierarchicalReportNode implements ReportNode<Iterable<ReportNode>> {

    private Iterable<ReportNode> values;
    private String name;

    public HierarchicalReportNode(String name, Iterable<ReportNode> subNodes) {
        this.name = name;
        values = subNodes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Iterable<ReportNode> getValue() {
        return values;
    }
}