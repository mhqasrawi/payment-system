package com.progressoft.jip.ui.web;

import javax.inject.Inject;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.progressoft.jip.DateRulesValidationProvider;
import com.progressoft.jip.MenuContextImpl;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
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
import com.progressoft.jip.ui.field.PaymentRulesField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;
import com.progressoft.jip.ui.webrendering.form.FormRenderer;
import com.progressoft.jip.ui.webrendering.form.impl.FormHtmlRenderer;

public class FormDemo {

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
	private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;

	@Inject
	private AccountPersistenceService accountService;

	@Inject
	private DateRulesValidationProvider dateRulesValidationProvider;

	private FileSystemXmlApplicationContext appContext;
	private FormRenderer formRenderer = new FormHtmlRenderer();

	private static final String APP_CONTEXT_LOCATION = "app.context.location";
	public static final PaymentMenuContext context = new MenuContextImpl();

	public FormDemo() {
		String appContextLocation = System.getProperty(APP_CONTEXT_LOCATION);
		appContext = new FileSystemXmlApplicationContext(appContextLocation);
		appContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	public String getForm() {

		FormImpl newAccountForm = new FormImpl(CREATE_NEW_ACCOUNT_FORM_DESCRIPTION) {
			@Override
			public Class getClassType() {
				return null;
			}
		};

		newAccountForm.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER)
				.setName(AccountDTOConstant.ACCOUNT_NUMBER_ACCOUNT_DTO));

		newAccountForm.addField(new StringField().setDescription(ENTER_ACCOUNT_NAME)
				.setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO));

		newAccountForm.addField(
				new CurrencyField().setDescription(ENTER_CURRENCY).setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO));

		newAccountForm.addField(
				new IBANField(ibanValidator).setDescription(ENTER_IBAN).setName(AccountDTOConstant.IBAN_ACCOUNT_DTO));

		newAccountForm.addField(new CustomerField().setDescription(ENTER_CUSTOMER_NAME)
				.setName(AccountDTOConstant.CUSTOMER_DTO_ACCOUNT_DTO));

		newAccountForm.addField(new PaymentRulesField(dateRulesValidationProvider)
				.setDescription(SELECT_PAYMENT_DATE_RULE).setName(AccountDTOConstant.ACCOUNT_PAYMENT_RULE));

		newAccountForm.addHiddenField(
				new IntegerField().setDescription("").setName(AccountDTOConstant.ID_NAME_ACCOUNT_DTO).setValue("0"));

		newAccountForm.addHiddenField(new AccountStatusField().setDescription("")
				.setName(AccountDTOConstant.ACCOUNT_STATUS_ACCOUNT_DTO).setValue(AccountStatus.ACTIVE.name()));

		return formRenderer.renderToHtml(newAccountForm);
	}
}
