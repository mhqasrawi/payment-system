package com.progressoft.jip;

import java.util.Arrays;

import javax.inject.Inject;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.account.service.AccountPersistenceServiceImpl;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.ui.dynamic.menu.ObjectProcessingStrategy;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;
import com.progressoft.jip.ui.menu.Menu;

public class MenusDefenation {

	private static final String PIKUP_ACCOUNT_MENU_DESCRIPTION = "Pikup Account";
	private static final String INSERT_NEW_ACCOUNT_MENU_DESCRIPTION = "Insert New Account";
	private static final PaymentPurposeDAO paymentPurposeService = null;

	static BasicDataSource dataSource = new BasicDataSource() {
		{
			setDriverClassName("com.mysql.cj.jdbc.Driver");
			setUrl("jdbc:mysql://localhost:3306/payment_db");
			setUsername("root");
			setPassword("root");
		}
	};
	static AccountPersistenceService jpaDummy = new AccountPersistenceServiceImpl(new JDBCAccountDAO(dataSource),
			new JDBCIBANDAO(dataSource));

	private SubmitAction<PaymentMenuContext, AccountDTO> UPDATE_ACCOUNT_INFO = (
			PaymentMenuContext menuContext, AccountDTO accountDTO) -> {
		menuContext.setCurrentAccount(accountService.save(accountDTO));
	};

	public static Menu<PaymentMenuContext> ADD_NEW_PAYMENT_PURPOSE = new PaymentFormToObjectBuilderImpl<PaymentPurposeDTO>()
			.setDescription("Insert New Payment Purpose").setForm(FormsDefenation.NEW_PAYMENT_PURPOSE_FORM)
			.setInterfaceType(PaymentPurposeDTO.class).setProcessingStrategy((menuContext, paymentPurpose) -> {
				paymentPurposeService.save(paymentPurpose);
			}).build();

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
