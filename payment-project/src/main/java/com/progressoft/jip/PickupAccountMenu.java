package com.progressoft.jip;

import java.util.List;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class PickupAccountMenu extends MenuImpl {

	private static final String PIKUP_ACCOUNT = "Pikup Account";

	public PickupAccountMenu(AccountPersistenceService accountPersistenceService, List<Menu> subMenus) {
		super(PIKUP_ACCOUNT, subMenus, new PikeupAccount(accountPersistenceService));
	}

	public PickupAccountMenu(AccountPersistenceService accountPersistenceService) {
		super(PIKUP_ACCOUNT, new PikeupAccount(accountPersistenceService));
	}

	private static class PikeupAccount implements Action {

		private static final String PLEASE_ENTER_YOUR_ACCOUNT_NUMBER = "Please Enter Your Account Number";
		private static final String ACCOUNT_NUMBER = "accountNumber";
		private static final String ENTER_ACCOUNT_INFO = "Enter Account Info";
		private AccountPersistenceService accountPersistenceService;

		public PikeupAccount(AccountPersistenceService accountPersistenceService) {
			this.accountPersistenceService = accountPersistenceService;
		}

		@Override
		public MenuContext doAction(MenuContext menuContext) {
			menuContext.put(MenuContext.FORM_VALUE, null);
			Form form = new FormImpl(ENTER_ACCOUNT_INFO).addField(
					new StringField().setName(ACCOUNT_NUMBER).setDescription(PLEASE_ENTER_YOUR_ACCOUNT_NUMBER));
			menuContext.getMenuMenager().renderForm(form);
			AccountDTO account = accountPersistenceService
					.getAccount((String) form.getFieldByName(ACCOUNT_NUMBER).getValue());
			menuContext.put(MenuContext.ACCOUNT_DTO, account);
			return menuContext;
		}

	}
}
