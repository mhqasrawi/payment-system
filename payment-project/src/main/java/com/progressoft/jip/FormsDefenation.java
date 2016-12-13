package com.progressoft.jip;

import org.springframework.beans.factory.annotation.Autowired;

import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.iban.impl.IBANFormatsReader;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.field.AccountStatusField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.CustomerField;
import com.progressoft.jip.ui.field.IBANField;
import com.progressoft.jip.ui.field.LongField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

public class FormsDefenation {

	private static final String ENTER_CUSTOMER_NAME = "Enter Customer Name";
	private static final String PAYMENT_DESCRIPTION = "description";
	private static final String PAYMENT_SHORT_CODE = "shortCode";
	private static final String ENTER_PAYMENT_DESCRIPTION = "Enter Payment Description";
	private static final String ENTER_PAYMENT_SHORT_CODE = "Enter Payment Short Code";
	private static final String CREATE_NEW_PAYMENT_PURPOSE = "Create New Payment Purpose";
	private static final String EDIT_ACCOUNT_CURRENCY = "Edit Account Currency";
	private static final String CREATE_NEW_ACCOUNT_FORM_DESCRIPTION = "Create New Account";
	private static final String PICKUP_ACCOUNT_FORM_DESCRIPTION = "Pikup Account";
	private static final String EDIT_ACCOUNT_NAME_FORM_DESCRIPTION = "Edit Account Name";
	private static final String ENTER_IBAN = "Enter IBAN";
	private static final String ENTER_CURRENCY = "Enter Currency";
	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String ENTER_ACCOUNT_NAME = "Enter Account Name";

	@Autowired
	private IBANValidator ibanValidator;
	@Autowired
	private IBANFormatsReader reader;

	public Form getNewAccountForm() {
		return new FormImpl(CREATE_NEW_ACCOUNT_FORM_DESCRIPTION)
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
				.addHiddenField(new LongField().setDescription("").setName(AccountDTOConstant.ID_NAME_ACCOUNT_DTO)
						.setValue("0"))
				.addHiddenField(new AccountStatusField().setDescription("")
						.setName(AccountDTOConstant.ACCOUNT_STATUS_ACCOUNT_DTO).setValue(AccountStatus.ACTIVE.name()));
	}

	public Form getNewPaymentPurpose() {
		return new FormImpl(CREATE_NEW_PAYMENT_PURPOSE)
				.addField(new StringField().setDescription(ENTER_PAYMENT_SHORT_CODE).setName(PAYMENT_SHORT_CODE))
				.addField(new StringField().setDescription(ENTER_PAYMENT_DESCRIPTION).setName(PAYMENT_DESCRIPTION));
	}

	public Form getPickupAccountForm() {
		return new FormImpl(PICKUP_ACCOUNT_FORM_DESCRIPTION)
				.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER).setName(StringContainer.VALUE_NAME));
	}

	public Form getEditAccountNameForm() {
		return new FormImpl(EDIT_ACCOUNT_NAME_FORM_DESCRIPTION).addField(new StringField()
				.setDescription(ENTER_ACCOUNT_NAME).setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO));
	}

	public Form getEditAccountCurrenyForm() {
		return new FormImpl(EDIT_ACCOUNT_CURRENCY).addField(
				new CurrencyField().setDescription(ENTER_CURRENCY).setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO));
	}
}
