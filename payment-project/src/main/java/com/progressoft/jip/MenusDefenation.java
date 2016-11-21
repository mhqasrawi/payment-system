package com.progressoft.jip;

import java.util.Arrays;

import org.apache.commons.dbcp2.BasicDataSource;

import com.progressoft.jip.payment.PaymentPurpose;
import com.progressoft.jip.payment.PaymentPurposeDAO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.impl.JDBCAccountDAO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.account.service.AccountPersistenceServiceImpl;
import com.progressoft.jip.payment.iban.dao.impl.JDBCIBANDAO;
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
			setPassword("root12");
		}
	};
	static AccountPersistenceService jpaDummy = new AccountPersistenceServiceImpl(new JDBCAccountDAO(dataSource),
			new JDBCIBANDAO(dataSource));

	public static ObjectProcessingStrategy<PaymentMenuContext, AccountDTO> UPDATE_ACCOUNT_INFO = (
			PaymentMenuContext menuContext, AccountDTO accountDTO) -> {
		menuContext.setCurrentAccount(jpaDummy.save(accountDTO));
	};

	public static Menu<PaymentMenuContext> ADD_NEW_PAYMENT_PURPOSE = new PaymentFormToObjectBuilderImpl<PaymentPurpose>()
			.setDescription("Insert New Payment Purpose").setForm(FormsDefenation.NEW_PAYMENT_PURPOSE_FORM)
			.setInterfaceType(PaymentPurpose.class).setProcessingStrategy((menuContext, paymentPurpose) -> {
				paymentPurposeService.save(paymentPurpose);
			}).build();

	public static Menu<PaymentMenuContext> EDIT_ACCOUNT_NAME_MENU = new PaymentFormToObjectBuilderImpl<AccountDTO>()
			.setDescription("Edit Account Name").setForm(FormsDefenation.EDIT_ACCOUNT_NAME_FORM)
			.setInterfaceType(AccountDTO.class).setProcessingStrategy((menuContext, accountDTO) -> {
				menuContext.setCurrentAccount(jpaDummy.save(accountDTO));
			}).buildEditMenu((context) -> {
				AccountDTO currentAccount = context.getCurrentAccount();
				return currentAccount;
			});

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
