package com.progressoft.jip.actions.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentEditAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.PaymentEditAction;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

/**
 * @author Ahmad.Jardat
 *
 */
@Configurable(autowire=Autowire.BY_TYPE)
public class EditAccountNameAction extends AbstractPaymentEditAction<AccountDTO> implements PaymentEditAction<AccountDTO> {

	private static final String ENTER_ACCOUNT_NAME = "Enter Account Name";
	private static final String EDIT_ACCOUNT_NAME_FORM_DESCRIPTION = "Edit Account Name";

	@Inject
	private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;
	@Inject
	private AccountPersistenceService accountService;

	@PostConstruct
	public void init() {
		Form editAccountNameForm = new FormImpl(EDIT_ACCOUNT_NAME_FORM_DESCRIPTION).addField(new StringField()
				.setDescription(ENTER_ACCOUNT_NAME).setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO));
		setAction(dynamicFormActionBuilder.setDefaultObjectStrategy(this)
				.setInterfaceType(AccountDTO.class).setSubmitAction(this).setForm(editAccountNameForm).build());
	}

	@Override
	public AccountDTO defaultValue(PaymentMenuContext context) {
		return context.getCurrentAccount();
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDTO) {
		accountService.save(accountDTO);
		menuContext.setCurrentAccount(accountDTO);
	}
}
