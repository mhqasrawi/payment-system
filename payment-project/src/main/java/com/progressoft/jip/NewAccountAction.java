package com.progressoft.jip;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANDTO;

public class NewAccountAction implements Action {

	private static final String FORM_VALUE = "FormValue";
	private Action action;
	private AccountPersistenceService accountPersistenceService;
	private Map<String, Object> values = new HashMap<>();

	public NewAccountAction(Action action) {
		this.action = action;
	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		menuContext.put(FORM_VALUE, null);
		action.doAction(menuContext);
		Form form = menuContext.get(FORM_VALUE);
		for (Field field : form.getFields()) {
			values.put(field.getName(), field.getValue());
		}
		return menuContext;
	}

	private class AccountDTOImpl implements AccountDTO {
		
		private String accountNumber;
		private IBANDTO iban;
		private String accountName;
		private Currency currency;
		private AccountStatus accountStatus = AccountStatus.ACTIVE;

		public void setCurrency(Currency currency) {
			this.currency = currency;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public void setIban(IBANDTO iban) {
			this.iban = iban;
		}

		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}

		public void setAccountStatus(AccountStatus accountStatus) {
			this.accountStatus = accountStatus;
		}

		@Override
		public long getId() {
			return 0;
		}

		@Override
		public String getAccountNumber() {
			return accountNumber;
		}

		@Override
		public IBANDTO getIban() {
			return iban;
		}

		@Override
		public String getAccountName() {
			return accountName;
		}

		@Override
		public Currency getCurreny() {
			return currency;
		}

		@Override
		public AccountStatus getAccountStatus() {
			return accountStatus;
		}

	}

}
