package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentNewAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.PaymentNewAction;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.core.ReportSettings;
import com.progressoft.jip.ui.field.PathField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

public class NewReportAction extends AbstractPaymentNewAction<ReportSettingWrapper>
		implements PaymentNewAction<ReportSettingWrapper> {

	@Inject
	private ReportManager reportManager;
	@Inject
	private PaymentDynamicFormActionBuilder<ReportSettingWrapper> dynamicFormActionBuilder;
	@Inject
	private PaymentDAO paymentDAO;

	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(ReportSettingWrapper.class).setSubmitAction(this)
				.setForm(getForm()).build());
	}

	private Form getForm() {
		FormImpl form = new FormImpl("Enter Report Info");
		form.addField(new StringField().setDescription("Enter Report File Name").setName("fileName"));
		form.addField(new PathField().setDescription("Enter Report Output Directory").setName("path"));
		return form;
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, ReportSettingWrapper object) {
		String accountNumber = menuContext.getCurrentAccount().getAccountNumber();
		Iterable<PaymentDTO> payments = paymentDAO.get(accountNumber);
		ReportSettings settings = new ReportSettings();
		settings.setPath(object.getPath());
		settings.setFileName(object.getFileName());
		settings.setFileExtention("xml");
		settings.setPayments(payments);
		reportManager.generateReport(settings);
	}
}