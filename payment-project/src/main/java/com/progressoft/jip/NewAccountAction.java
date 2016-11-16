package com.progressoft.jip;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANDTO;

public class NewAccountAction implements Action {

	
	private Action action;
	private AccountPersistenceService accountPersistenceService;
	private Map<String, Object> values = new HashMap<>();

	public NewAccountAction(Action action, AccountPersistenceService accountPersistenceService) {
		this.action = action;
		this.accountPersistenceService = accountPersistenceService;
	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		menuContext.put(MenuContext.FORM_VALUE, null);
		action.doAction(menuContext);
		Form form = menuContext.get(MenuContext.FORM_VALUE);
		AccountDTOImpl accountDTO = new AccountDTOImpl();
		for (Field field : form.getFields()) {
			if (field.getName().equals("accountNumber")) {
				accountDTO.setAccountNumber((String) field.getValue());
			} else if (field.getName().equals("iban")) {
				accountDTO.setIban((IBANDTO) field.getValue());
			} else if (field.getName().equals("accountName")) {
				accountDTO.setAccountName((String) field.getValue());
			} else if (field.getName().equals("currency")) {
				accountDTO.setCurrency((Currency) field.getValue());
			}
		}
		accountPersistenceService.save(accountDTO);
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
