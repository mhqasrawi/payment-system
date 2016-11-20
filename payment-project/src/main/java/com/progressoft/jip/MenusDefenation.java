package com.progressoft.jip;

import java.util.Arrays;

import com.progressoft.jip.payment.PaymentPurpose;
import com.progressoft.jip.payment.PaymentPurposeDAO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.ObjectProcessingStrategy;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.menu.Menu;

public class MenusDefenation {

	private static final String PIKUP_ACCOUNT_MENU_DESCRIPTION = "Pikup Account";

	private static final String INSERT_NEW_ACCOUNT_MENU_DESCRIPTION = "Insert New Account";
	private static final PaymentPurposeDAO paymentPurposeService = null;
	
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

	public static ObjectProcessingStrategy<PaymentMenuContext, AccountDTO> UPDATE_ACCOUNT_INFO = (
			PaymentMenuContext menuContext, AccountDTO accountDTO) -> {
		jpaDummy.save(accountDTO);
		menuContext.setCurrentAccount(accountDTO);
	};

	public static Menu<PaymentMenuContext> ADD_NEW_PAYMENT_PURPOSE = new PaymentFormToObjectBuilderImpl<PaymentPurpose>()
			.setDescription("Insert New Payment Purpose").setForm(FormsDefenation.NEW_PAYMENT_PRPOSE_FORM)
			.setInterfaceType(PaymentPurpose.class).setProcessingStrategy((menuContext, paymentPurpose) -> {
				paymentPurposeService.save(paymentPurpose);
			}).build();

	public static Menu<PaymentMenuContext> EDIT_ACCOUNT_NAME_MENU = new PaymentFormToObjectBuilderImpl<AccountDTO>()
			.setDescription("Edit Account Name").setForm(FormsDefenation.EDIT_ACCOUNT_NAME_FORM)
			.setInterfaceType(AccountDTO.class).setProcessingStrategy((menuContext, accountDTO) -> {
				jpaDummy.save(accountDTO);
				menuContext.setCurrentAccount(accountDTO);
			}).buildEditMenu((context) -> context.getCurrentAccount());

	public static Menu<PaymentMenuContext> EDIT_ACCOUNT_CURRENCY_MENU = new PaymentFormToObjectBuilderImpl<AccountDTO>()
			.setDescription("Edit Account Currency").setForm(FormsDefenation.EDIT_ACCOUNT_CURRENY_FORM)
			.setInterfaceType(AccountDTO.class).setProcessingStrategy(UPDATE_ACCOUNT_INFO)
			.buildEditMenu((context) -> context.getCurrentAccount());

	public static Menu<PaymentMenuContext> ADD_NEW_ACCOUNT_MENU = new PaymentFormToObjectBuilderImpl<AccountDTO>()
			.setDescription(INSERT_NEW_ACCOUNT_MENU_DESCRIPTION).setForm(FormsDefenation.NEW_ACCOUNT_FORM)
			.setInterfaceType(AccountDTO.class).setProcessingStrategy(UPDATE_ACCOUNT_INFO).build();

	public static Menu<PaymentMenuContext> PICKUP_ACCOUNT_MENU = new PaymentFormToObjectBuilderImpl<StringContainer>()
			.setDescription(PIKUP_ACCOUNT_MENU_DESCRIPTION).setForm(FormsDefenation.PICKUP_ACCOUNT_FORM)
			.setInterfaceType(StringContainer.class).setProcessingStrategy((menuContext, stringContainer) -> {
				AccountDTO account = jpaDummy.getAccount(stringContainer.getString());
				if (account == null) {
					throw new RuntimeException("Can't find Account With Number " + stringContainer.getString());
				}
				menuContext.setCurrentAccount(account);
			}).setSubMenu(Arrays.asList(EDIT_ACCOUNT_NAME_MENU, EDIT_ACCOUNT_CURRENCY_MENU)).build();

}
