package com.progressoft.jip.payment.report.core;

import com.progressoft.jip.payment.PaymentDTO;

public interface ReportNodeProvider {
	
	@SuppressWarnings("rawtypes")
	Iterable<ReportNode> getNodes(PaymentDTO payment);
	
	void setReportSettings(ReportSettingsSpi settings);
	
}
