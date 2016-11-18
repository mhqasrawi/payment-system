package com.progressoft.jip;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.FormToObjectMenu.FormToObjectBuilder;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.field.AccountStatusField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.IBANField;
import com.progressoft.jip.ui.field.LongField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

public class Menus {

	static AccountPersistenceService jpaDummy = new AccountPersistenceService() {

		private AccountDTO accountDTO;

		@Override
		public AccountDTO save(AccountDTO accountDTO) {
			System.out.println(accountDTO.getAccountName());
			System.out.println(accountDTO.getAccountNumber());
			System.out.println(accountDTO.getAccountStatus());
			System.out.println(accountDTO.getCurreny());
			System.out.println(accountDTO.getIban());
			this.accountDTO = accountDTO;
			return accountDTO;
		}

		@Override
		public AccountDTO getById(String id) {
			return null;
		}

		@Override
		public Iterable<AccountDTO> getAll() {
			return null;
		}

		@Override
		public AccountDTO getAccount(String accountNumber) {
			return accountDTO;
		}
	};

	public static FormImpl NEW_ACCOUNT_FORM = new FormImpl("Create New Account")
			.addField(new StringField().setDescription("Enter Account Number").setName("accountNumber"))
			.addField(new StringField().setDescription("Enter Account Name").setName("accountName"))
			.addField(new CurrencyField().setDescription("Enter Currency").setName("curreny"))
			.addField(new IBANField(System.out::println).setDescription("Enter IBAN").setName("iban"))
			.addHiddenField(new LongField().setDescription("").setName("id").setValue("1"))
			.addHiddenField(new AccountStatusField().setDescription("").setName("accountStatus").setValue("ACTIVE"));

	public static FormToObjectBuilder<PaymentMenuContext, AccountDTO> ADD_NEW_ACCOUNT_MENU = new FormToObjectBuilder<PaymentMenuContext, AccountDTO>()
			.setDescription("Insert New Account").setForm(NEW_ACCOUNT_FORM).setInterfaceType(AccountDTO.class)
			.setProcessingStrategy(jpaDummy::save);

	public static FormImpl PICKUP_ACCOUNT_FORM = new FormImpl("Pikup Account")
			.addField(new StringField().setDescription("Enter Account Number").setName(StringContainer.VALUE_NAME));

	public static FormToObjectBuilder<PaymentMenuContext, StringContainer> PICKUP_ACCOUNT_MENU = new FormToObjectBuilder<PaymentMenuContext, StringContainer>()
			.setDescription("Pikup Account").setForm(PICKUP_ACCOUNT_FORM).setInterfaceType(StringContainer.class)
			.setProcessingStrategy((c) -> {
				jpaDummy.getAccount(c.getString());
			});

}
