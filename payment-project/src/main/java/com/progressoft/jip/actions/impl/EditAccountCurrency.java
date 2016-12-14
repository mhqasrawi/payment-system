package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentEditAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.PaymentEditAction;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

/**
 * @author Ahmad.Jardat
 *
 */
public class EditAccountCurrency extends AbstractPaymentEditAction<AccountDTO>
		implements PaymentEditAction<AccountDTO> {

	private static final String EDIT_ACCOUNT_CURRENCY = "Edit Account Currency";
	private static final String ENTER_CURRENCY = "Enter Currency";

	@Inject
	private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;
	@Inject
	private AccountPersistenceService accountService;

	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(AccountDTO.class).setDefaultObjectStrategy(this)
				.setForm(getForm()).setSubmitAction(this).build());
	}

	private Action<PaymentMenuContext> getForm(Form form) {
		return dynamicFormActionBuilder.setInterfaceType(AccountDTO.class).setDefaultObjectStrategy(this).setForm(form)
				.setSubmitAction(this).build();
	}

	private Form getForm() {
		Form form = new FormImpl(EDIT_ACCOUNT_CURRENCY).addField(
				new CurrencyField().setDescription(ENTER_CURRENCY).setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO));
		return form;
	}

	@Override
	public AccountDTO defaultValue(PaymentMenuContext context) {
		return context.getCurrentAccount();
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDto) {
		menuContext.setCurrentAccount(accountService.save(accountDto));
	}

}
