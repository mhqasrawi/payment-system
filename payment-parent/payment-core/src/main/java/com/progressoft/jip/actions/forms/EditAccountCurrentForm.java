package com.progressoft.jip.actions.forms;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.DefaultValueProvider;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.form.FormImpl;

public class EditAccountCurrentForm extends FormImpl<PaymentMenuContext, AccountDTO>
		implements DefaultValueProvider<PaymentMenuContext, AccountDTO>, SubmitAction<PaymentMenuContext, AccountDTO> {

	private static final String EDIT_ACCOUNT_CURRENCY = "Edit Account Currency";
	private static final String ENTER_CURRENCY = "Enter Currency";
	
	@Inject
	private AccountPersistenceService accountService;
	
	public EditAccountCurrentForm() {
		super(EDIT_ACCOUNT_CURRENCY);
	}

	public void init(){
		CurrencyField currencyField = new CurrencyField();
		currencyField.setDescription(ENTER_CURRENCY);
		currencyField.setName(AccountDTOConstant.CURRENY_ACCOUNT_DTO);
		addField(currencyField);
	}
	
	@Override
	public AccountDTO defaultValue(PaymentMenuContext menuContext) {
		return menuContext.getCurrentAccount();
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDto) {
		menuContext.setCurrentAccount(accountService.save(accountDto));
	}

	@Override
	public Class<AccountDTO> getClassType() {
		return AccountDTO.class;
	}

}
