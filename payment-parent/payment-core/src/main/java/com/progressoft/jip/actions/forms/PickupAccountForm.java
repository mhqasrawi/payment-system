package com.progressoft.jip.actions.forms;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.usecase.PikupAccountUseCase;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

public class PickupAccountForm extends FormImpl<PaymentMenuContext, StringContainer>
		implements SubmitAction<PaymentMenuContext, StringContainer> {

	private static final String ENTER_ACCOUNT_NUMBER = "Enter Account Number";
	private static final String PICKUP_ACCOUNT_FORM_DESCRIPTION = "Pikup Account";

	@Inject
	private AccountPersistenceService accountService;

	public PickupAccountForm() {
		super(PICKUP_ACCOUNT_FORM_DESCRIPTION);
	}

	public void init() {
		this.addField(new StringField().setDescription(ENTER_ACCOUNT_NUMBER).setName(StringContainer.VALUE_NAME));
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, StringContainer stringContainer) {
		new PikupAccountUseCase(menuContext, accountService).loadAccountByAccountNumber(stringContainer.getString());

	}

	@Override
	public Class<StringContainer> getClassType() {
		return StringContainer.class;
	}

}
