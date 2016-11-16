package com.progressoft.jip;

import java.util.Arrays;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class Main {

	public static MenuRenderManger renderManger = new ConsoleMenuRender(System.in, System.out);

	public static void main(String[] args) {
		Form form = new FormImpl("Enter IBAN Number");
		StringField accountNumber = new StringField();
		accountNumber.setDescription("Enter Account Number");
		accountNumber.setName("accountNumber");
		form.addField(accountNumber);
		StringField accountName = new StringField();
		accountName.setDescription("Enter Account Name");
		accountName.setName("accountName");
		form.addField(accountName);
		CurrencyField currency = new CurrencyField();
		currency.setDescription("Enter Currency");
		currency.setName("currency");
		form.addField(currency);
		IBANField iban = new IBANField(System.out::println);
		iban.setDescription("Enter IBAN");
		iban.setName("iban");
		form.addField(iban);

		
		Menu mainMenu = new MenuImpl("Choice Option",
				Arrays.asList(new MenuImpl("Accses Your Account", Arrays.asList(new MenuImpl("Sub Menu From Account",
						new NewAccountAction(new ShowFormAction(renderManger, form), jpaDummy))), (n) -> {
							System.out.println("You Enter Accsess Your Account Option");
							return null;
						})));
		renderManger.renderMenu(mainMenu);
	}

	static AccountPersistenceService jpaDummy = new AccountPersistenceService() {
		
		@Override
		public AccountDTO save(AccountDTO accountDTO) {
			System.out.println(accountDTO.getAccountName());
			System.out.println(accountDTO.getAccountNumber());
			System.out.println(accountDTO.getAccountStatus());
			System.out.println(accountDTO.getCurreny());
			System.out.println(accountDTO.getIban());
			return null;
		}
		
		@Override
		public AccountDTO getById(String id) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Iterable<AccountDTO> getAll() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccountDTO getAccount(String accountNumber) {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
