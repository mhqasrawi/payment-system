package com.progressoft.jip;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANDTO;

public class InsertNewAccountMenu extends MenuImpl {

	private static final String INSERT_NEW_ACCOUNT = "Insert New Account";

	public InsertNewAccountMenu(AccountPersistenceService accountPersistenceService) {
		super(INSERT_NEW_ACCOUNT,
				new NewAccountAction(new ShowFormAction(new AccountForm()), accountPersistenceService));
	}

	public static class NewAccountAction implements Action {

		private Action showFormAction;
		private AccountPersistenceService accountPersistenceService;
		private Map<String, Object> values = new HashMap<>();

		public NewAccountAction(ShowFormAction showFromAction, AccountPersistenceService accountPersistenceService) {
			this.showFormAction = showFromAction;
			this.accountPersistenceService = accountPersistenceService;
		}

		@Override
		public MenuContext doAction(MenuContext menuContext) {
			menuContext.put(MenuContext.FORM_VALUE, null);
			showFormAction.doAction(menuContext);
			Form form = menuContext.get(MenuContext.FORM_VALUE);
			AccountDTOImpl accountDTO = new AccountDTOImpl();
			for (Field<?> field : form.getFields()) {
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

	public static class AccountForm extends FormImpl {

		public AccountForm() {
			super("Create New Account");
			addField(new StringField().setDescription("Enter Account Number").setName("accountNumber"));
			addField(new StringField().setDescription("Enter Account Name").setName("accountName"));
			addField(new CurrencyField().setDescription("Enter Currency").setName("currency"));
			addField(new IBANField(System.out::println).setDescription("Enter IBAN").setName("iban"));
		}

	}

}
