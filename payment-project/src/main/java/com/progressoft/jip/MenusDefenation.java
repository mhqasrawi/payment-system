package com.progressoft.jip;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Ignore;

import com.progressoft.jip.payment.PaymentPurpose;
import com.progressoft.jip.payment.PaymentPurposeDAO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.menu.Menu;

public class MenusDefenation {

	private static final String PIKUP_ACCOUNT_MENU_DESCRIPTION = "Pikup Account";
	private static final String INSERT_NEW_ACCOUNT_MENU_DESCRIPTION = "Insert New Account";
	private static final PaymentPurposeDAO paymentPurposeService = null;

	@Inject
	private AccountPersistenceService accountService;
	@Inject
	private FormsDefenation formsDefenation;

	private SubmitAction<PaymentMenuContext, AccountDTO> UPDATE_ACCOUNT_INFO = (
			PaymentMenuContext menuContext, AccountDTO accountDTO) -> {
		menuContext.setCurrentAccount(accountService.save(accountDTO));
	};

	public SubmitAction<PaymentMenuContext, AccountDTO> getUPDATE_ACCOUNT_INFO() {
		return UPDATE_ACCOUNT_INFO;
	}

	public void setUPDATE_ACCOUNT_INFO(SubmitAction<PaymentMenuContext, AccountDTO> uPDATE_ACCOUNT_INFO) {
		UPDATE_ACCOUNT_INFO = uPDATE_ACCOUNT_INFO;
	}

	
	public Menu<PaymentMenuContext> getADD_NEW_PAYMENT_PURPOSE() {
		return new PaymentFormToObjectBuilderImpl<PaymentPurpose>().setDescription("Insert New Payment Purpose")
				.setForm(formsDefenation.getNewPaymentPurpose()).setInterfaceType(PaymentPurpose.class)
				.setProcessingStrategy((menuContext, paymentPurpose) -> {
					paymentPurposeService.save(paymentPurpose);
				}).build();
	}

	@Ignore
	public Menu<PaymentMenuContext> getEDIT_ACCOUNT_NAME_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>().setDescription("Edit Account Name")
				.setForm(formsDefenation.getEditAccountNameForm()).setInterfaceType(AccountDTO.class)
				.setProcessingStrategy((menuContext, accountDTO) -> {
					menuContext.setCurrentAccount(accountService.save(accountDTO));
				}).buildEditMenu((context) -> {
					AccountDTO currentAccount = context.getCurrentAccount();
					return currentAccount;
				});
	}

	@Ignore
	public Menu<PaymentMenuContext> getEDIT_ACCOUNT_CURRENCY_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>().setDescription("Edit Account Currency")
				.setForm(formsDefenation.getEditAccountCurrenyForm()).setInterfaceType(AccountDTO.class)
				.setProcessingStrategy(UPDATE_ACCOUNT_INFO).buildEditMenu((context) -> context.getCurrentAccount());
	}

	@Ignore
	public Menu<PaymentMenuContext> getADD_NEW_ACCOUNT_MENU() {
		return new PaymentFormToObjectBuilderImpl<AccountDTO>().setDescription(INSERT_NEW_ACCOUNT_MENU_DESCRIPTION)
				.setForm(formsDefenation.getNewAccountForm()).setInterfaceType(AccountDTO.class)
				.setProcessingStrategy(UPDATE_ACCOUNT_INFO).build();
	}

	@Ignore
	public Menu<PaymentMenuContext> getPICKUP_ACCOUNT_MENU() {
		return new PaymentFormToObjectBuilderImpl<StringContainer>().setDescription(PIKUP_ACCOUNT_MENU_DESCRIPTION)
				.setForm(formsDefenation.getPickupAccountForm()).setInterfaceType(StringContainer.class)
				.setProcessingStrategy((menuContext, stringContainer) -> {
					AccountDTO account = accountService.getAccount(stringContainer.getString());
					if (account == null) {
						throw new RuntimeException("Can't find Account With Number " + stringContainer.getString());
					}
					menuContext.setCurrentAccount(account);
				})
				.setSubMenu(Arrays.asList(new PaymentFormToObjectBuilderImpl<AccountDTO>()
						.setDescription("Edit Account Name").setForm(formsDefenation.getEditAccountNameForm())
						.setInterfaceType(AccountDTO.class).setProcessingStrategy((menuContext, accountDTO) -> {
							menuContext.setCurrentAccount(accountService.save(accountDTO));
						}).buildEditMenu((context) -> {
							AccountDTO currentAccount = context.getCurrentAccount();
							return currentAccount;
						}), getEDIT_ACCOUNT_CURRENCY_MENU()))
				.build();
	}
}
