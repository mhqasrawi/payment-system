package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.NewAccountForm;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.ui.action.Action;

/**
 * @author Ahmad.Jardat
 *
 */
public class NewAccountAction implements PaymentAction {

	@Inject
	private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;

	private NewAccountForm accountForm = new NewAccountForm();

	@Override
	public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
		Action<PaymentMenuContext> action = dynamicFormActionBuilder.refresh().setInterfaceType(AccountDTO.class)
				.setSubmitAction(accountForm).setForm(accountForm).build();
		return action.doAction(menuContext);
	}

}
