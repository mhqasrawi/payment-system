package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.PikupAccountUseCase;
import com.progressoft.jip.actions.AbstractPaymentNewAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

public class PickupAccountAction extends AbstractPaymentNewAction<StringContainer> {

	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String PICKUP_ACCOUNT_FORM_DESCRIPTION = "Pikup Account";

	@Inject
	private AccountPersistenceService accountService;
	@Inject
	private PaymentDynamicFormActionBuilder<StringContainer> dynamicFormActionBuilder;

	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(StringContainer.class).setSubmitAction(this)
				.setForm(getForm()).build());
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, StringContainer stringContainer) {
		menuContext.setCurrentAccount(
				new PikupAccountUseCase(accountService).getAccountByAccountNumber(stringContainer.getString()));
	}

	private FormImpl getForm() {
		return new FormImpl(PICKUP_ACCOUNT_FORM_DESCRIPTION)
				.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER).setName(StringContainer.VALUE_NAME));
	}

}
