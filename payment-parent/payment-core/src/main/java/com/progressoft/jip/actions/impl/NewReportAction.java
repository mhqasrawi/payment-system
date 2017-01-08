package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.NewReportForm;
import com.progressoft.jip.ui.action.Action;

public class NewReportAction implements PaymentAction {

	@Inject
	private PaymentDynamicFormActionBuilder<ReportSettingWrapper> dynamicFormActionBuilder;
	
	private NewReportForm form = new NewReportForm();

	public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
		Action<PaymentMenuContext> action = dynamicFormActionBuilder.refresh().setInterfaceType(ReportSettingWrapper.class)
				.setSubmitAction(form).setForm(form).build();
		return action.doAction(menuContext);
	}

}