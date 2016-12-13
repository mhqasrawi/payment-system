package com.progressoft.jip.actions;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.action.Action;

public abstract class AbstractPaymentAction<T> implements PaymentNewAction<T> {

	private PaymentAction action;

	protected void setAction(Action<PaymentMenuContext> paymentAction) {
		action = (PaymentAction) paymentAction;
	}

	public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
		return action.doAction(menuContext);
	}
}
