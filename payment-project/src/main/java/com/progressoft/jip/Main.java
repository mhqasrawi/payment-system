package com.progressoft.jip;

import java.util.Arrays;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class Main {

	public static MenuRenderManger renderManger = new ConsoleMenuRender(System.in, System.out);

	public static void main(String[] args) {

		MainMenu mainMenu = new MainMenu(Arrays.asList(new PickupAccountMenu(jpaDummy),
				new InsertNewAccountMenu(jpaDummy), new MenuImpl("Exit", (c) -> {
					System.exit(0);
					return c;
				})));
		renderManger.renderMenu(mainMenu);
	}

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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AccountDTO getAccount(String accountNumber) {
			return accountDTO;
		}
	};
}
