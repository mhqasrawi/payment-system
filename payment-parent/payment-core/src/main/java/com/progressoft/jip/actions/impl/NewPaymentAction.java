package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.NewPaymentForm;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.ui.action.Action;

public class NewPaymentAction implements Action<PaymentMenuContext> {

	@Inject
	private PaymentDynamicFormActionBuilder<PaymentInfo> dynamicFormActionBuilder;

	private NewPaymentForm newPaymentAction = new NewPaymentForm();

	@Override
	public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
		Action<PaymentMenuContext> action = dynamicFormActionBuilder.refresh().setInterfaceType(PaymentInfo.class).setDefaultObjectStrategy(newPaymentAction)
				.setForm(newPaymentAction).setSubmitAction(newPaymentAction).build();
		return action.doAction(menuContext);
	}

}
