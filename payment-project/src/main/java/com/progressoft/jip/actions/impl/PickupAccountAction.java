package com.progressoft.jip.actions.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentNewAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

@Configurable(autowire = Autowire.BY_TYPE)
public class PickupAccountAction extends AbstractPaymentNewAction<StringContainer> {

	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String PICKUP_ACCOUNT_FORM_DESCRIPTION = "Pikup Account";

	@Inject
	private AccountPersistenceService accountService;
	@Inject
	private PaymentDynamicFormActionBuilder<StringContainer> dynamicFormActionBuilder;

	@PostConstruct
	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(StringContainer.class).setSubmitAction(this)
				.setForm(getForm())
				.build());
	}


	@Override
	public void submitAction(PaymentMenuContext menuContext, StringContainer stringContainer) {
		AccountDTO account = accountService.getAccount(stringContainer.getString());
		if (account == null) {
			throw new RuntimeException("Can't find Account With Number " + stringContainer.getString());
		}
		menuContext.setCurrentAccount(account);
	}

	private FormImpl getForm() {
		return new FormImpl(PICKUP_ACCOUNT_FORM_DESCRIPTION).addField(
				new StringField().setDescription(ENTER_ACCOUNT_NUMBER).setName(StringContainer.VALUE_NAME));
	}

}
