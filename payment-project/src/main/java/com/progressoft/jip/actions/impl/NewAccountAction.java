package com.progressoft.jip.actions.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentNewAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.PaymentNewAction;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.ui.field.AccountStatusField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.CustomerField;
import com.progressoft.jip.ui.field.IBANField;
import com.progressoft.jip.ui.field.IntegerField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

/**
 * @author Ahmad.Jardat
 *
 */
@Configurable(autowire=Autowire.BY_TYPE,dependencyCheck=true)
public class NewAccountAction extends AbstractPaymentNewAction<AccountDTO> implements PaymentNewAction<AccountDTO> {

	private static final String ENTER_CUSTOMER_NAME = "Enter Customer Name";
	private static final String CREATE_NEW_ACCOUNT_FORM_DESCRIPTION = "Create New Account";
	private static final String ENTER_IBAN = "Enter IBAN";
	private static final String ENTER_CURRENCY = "Enter Currency";
	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String ENTER_ACCOUNT_NAME = "Enter Account Name";

	@Inject
	private IBANValidator ibanValidator;
	@Inject
	private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;
	@Inject
	private AccountPersistenceService accountService;

	@PostConstruct
	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(AccountDTO.class).setSubmitAction(this)
				.setForm(getNewAccountForm()).build());
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDto) {
		AccountDTO save = accountService.save(accountDto);
		menuContext.setCurrentAccount(save);
	}

	private Form getNewAccountForm() {
		Form newAccountForm = new FormImpl(CREATE_NEW_ACCOUNT_FORM_DESCRIPTION)
				.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER)
						.setName(AccountDTOConstant.ACCOUNT_NUMBER_ACCOUNT_DTO))
				.addField(new StringField().setDescription(ENTER_ACCOUNT_NAME)
						.setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO))
				.addField(new CurrencyField().setDescription(ENTER_CURRENCY)
						.setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO))
				.addField(new IBANField(ibanValidator).setDescription(ENTER_IBAN)
						.setName(AccountDTOConstant.IBAN_ACCOUNT_DTO))
				.addField(new CustomerField().setDescription(ENTER_CUSTOMER_NAME)
						.setName(AccountDTOConstant.CUSTOMER_DTO_ACCOUNT_DTO))
				.addHiddenField(new IntegerField().setDescription("").setName(AccountDTOConstant.ID_NAME_ACCOUNT_DTO)
						.setValue("0"))
				.addHiddenField(new AccountStatusField().setDescription("")
						.setName(AccountDTOConstant.ACCOUNT_STATUS_ACCOUNT_DTO).setValue(AccountStatus.ACTIVE.name()));
		;
		return newAccountForm;
	}
}
