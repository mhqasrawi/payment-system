package com.progressoft.jip;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private DataSource dataSource;
	@Autowired
	private AccountPersistenceService accountService;
	@Autowired
	private FormsDefenation formsDefenation;

	private ObjectProcessingStrategy<PaymentMenuContext, AccountDTO> UPDATE_ACCOUNT_INFO = (
			PaymentMenuContext menuContext, AccountDTO accountDTO) -> {
		menuContext.setCurrentAccount(accountService.save(accountDTO));
	};

	public ObjectProcessingStrategy<PaymentMenuContext, AccountDTO> getUPDATE_ACCOUNT_INFO() {
		return UPDATE_ACCOUNT_INFO;
	}

	public void setUPDATE_ACCOUNT_INFO(ObjectProcessingStrategy<PaymentMenuContext, AccountDTO> uPDATE_ACCOUNT_INFO) {
		UPDATE_ACCOUNT_INFO = uPDATE_ACCOUNT_INFO;
	}

	public Menu<PaymentMenuContext> getADD_NEW_PAYMENT_PURPOSE() {
		return new PaymentFormToObjectBuilderImpl<PaymentPurpose>()
				.setDescription("Insert New Payment Purpose").setForm(formsDefenation.getNewPaymentPurpose())
				.setInterfaceType(PaymentPurpose.class).setProcessingStrategy((menuContext, paymentPurpose) -> {
					paymentPurposeService.save(paymentPurpose);
				}).build();
	}

	public Menu<PaymentMenuContext> getEDIT_ACCOUNT_NAME_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>()
				.setDescription("Edit Account Name").setForm(formsDefenation.getEditAccountNameForm())
				.setInterfaceType(AccountDTO.class).setProcessingStrategy((menuContext, accountDTO) -> {
					menuContext.setCurrentAccount(accountService.save(accountDTO));
				}).buildEditMenu((context) -> {
					AccountDTO currentAccount = context.getCurrentAccount();
					return currentAccount;
				});
	}

	public Menu<PaymentMenuContext> getEDIT_ACCOUNT_CURRENCY_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>()
				.setDescription("Edit Account Currency").setForm(formsDefenation.getEditAccountCurrenyForm())
				.setInterfaceType(AccountDTO.class).setProcessingStrategy(UPDATE_ACCOUNT_INFO)
				.buildEditMenu((context) -> context.getCurrentAccount());
	}

	public Menu<PaymentMenuContext> getADD_NEW_ACCOUNT_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>()
				.setDescription(INSERT_NEW_ACCOUNT_MENU_DESCRIPTION).setForm(formsDefenation.getNewAccountForm())
				.setInterfaceType(AccountDTO.class).setProcessingStrategy(UPDATE_ACCOUNT_INFO).build();
	}
	
	public Menu<PaymentMenuContext> getPICKUP_ACCOUNT_MENU() {
		return new PaymentFormToObjectBuilderImpl<StringContainer>()
				.setDescription(PIKUP_ACCOUNT_MENU_DESCRIPTION).setForm(formsDefenation.getPickupAccountForm())
				.setInterfaceType(StringContainer.class).setProcessingStrategy((menuContext, stringContainer) -> {
					AccountDTO account = accountService.getAccount(stringContainer.getString());
					if (account == null) {
						throw new RuntimeException("Can't find Account With Number " + stringContainer.getString());
					}
					menuContext.setCurrentAccount(account);
				}).setSubMenu(Arrays.asList(new PaymentFormToObjectBuilderImpl<AccountDTO>()
						.setDescription("Edit Account Name").setForm(formsDefenation.getEditAccountNameForm())
						.setInterfaceType(AccountDTO.class).setProcessingStrategy((menuContext, accountDTO) -> {
							menuContext.setCurrentAccount(accountService.save(accountDTO));
						}).buildEditMenu((context) -> {
							AccountDTO currentAccount = context.getCurrentAccount();
							return currentAccount;
						}), getEDIT_ACCOUNT_CURRENCY_MENU())).build();
	}
}
