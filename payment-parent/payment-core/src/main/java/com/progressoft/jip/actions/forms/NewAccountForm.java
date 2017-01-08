package com.progressoft.jip.actions.forms;

import javax.inject.Inject;

import com.progressoft.jip.DateRulesValidationProvider;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.usecase.NewAccountUseCase;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.AbstractConditionalExtraField;
import com.progressoft.jip.ui.field.AbstractField;
import com.progressoft.jip.ui.field.AccountStatusField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.CustomerField;
import com.progressoft.jip.ui.field.IBANField;
import com.progressoft.jip.ui.field.IntegerField;
import com.progressoft.jip.ui.field.PaymentRulesField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

public class NewAccountForm extends FormImpl<PaymentMenuContext,AccountDTO> implements SubmitAction<PaymentMenuContext, AccountDTO>{

	public static final String IS_THERE_EXTRA_INFO = "IsThereExtraInfo";

	private static final String SELECT_PAYMENT_DATE_RULE = "Select Payment Date Rule";
	private static final String ENTER_CUSTOMER_NAME = "Enter Customer Name";
	private static final String CREATE_NEW_ACCOUNT_FORM_DESCRIPTION = "Create New Account";
	private static final String ENTER_IBAN = "Enter IBAN";
	private static final String ENTER_CURRENCY = "Enter Currency";
	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String ENTER_ACCOUNT_NAME = "Enter Account Name";
	
	
	@Inject
	private IBANValidator ibanValidator;
	@Inject
	private AccountPersistenceService accountService;
	@Inject
	private DateRulesValidationProvider dateRulesValidationProvider;

	
	public NewAccountForm() {
		super(CREATE_NEW_ACCOUNT_FORM_DESCRIPTION);
	}

	

	@Override
	public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDto) {
		new NewAccountUseCase(accountService).process(menuContext, accountDto);
	}

	public void init() {
		this.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER)
				.setName(AccountDTOConstant.ACCOUNT_NUMBER_ACCOUNT_DTO));
		this.addField(new StringField().setDescription(ENTER_ACCOUNT_NAME)
				.setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO));
		this.addField(
				new CurrencyField().setDescription(ENTER_CURRENCY).setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO));
		this.addField(
				new IBANField(ibanValidator).setDescription(ENTER_IBAN).setName(AccountDTOConstant.IBAN_ACCOUNT_DTO));
		this.addField(new CustomerField().setDescription(ENTER_CUSTOMER_NAME)
				.setName(AccountDTOConstant.CUSTOMER_DTO_ACCOUNT_DTO));
		this.addField(new PaymentRulesField(dateRulesValidationProvider)
				.setDescription(SELECT_PAYMENT_DATE_RULE).setName(AccountDTOConstant.ACCOUNT_PAYMENT_RULE));
		this.addField(
				new NumberOfDay().setDescription("Enter Number Of Day").setName(AccountDTOConstant.PAYMENT_RULE_INFO));
		this.addHiddenField(
				new IntegerField().setDescription("").setName(AccountDTOConstant.ID_NAME_ACCOUNT_DTO).setValue("0"));
		this.addHiddenField(new AccountStatusField().setDescription("")
				.setName(AccountDTOConstant.ACCOUNT_STATUS_ACCOUNT_DTO).setValue(AccountStatus.ACTIVE.name()));
	}
	
	public static class NumberOfDay extends AbstractConditionalExtraField<String, PaymentMenuContext> {

		@Override
		public boolean isVisiable(PaymentMenuContext menuContext) {
			Object value = menuContext.get(IS_THERE_EXTRA_INFO);
			if (value == null){
				this.value = "0";
				return false;
			}
			else {
				return (Boolean) value;
			}
		}

		@Override
		public AbstractField<String> setValue(String value) {
			this.value = Integer.valueOf(value).toString();
			return this;
		}

	}

	@Override
	public Class<AccountDTO> getClassType() {
		return AccountDTO.class;
	}
}
