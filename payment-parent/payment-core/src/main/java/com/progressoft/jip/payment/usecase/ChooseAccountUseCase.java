package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class ChooseAccountUseCase {

	private final AccountPersistenceService accountService;
	private final PaymentMenuContext menuContext;

	public ChooseAccountUseCase(PaymentMenuContext menuContext, AccountPersistenceService accountService) {
		this.accountService = accountService;
		this.menuContext = menuContext;
	}

	public AccountDTO loadAccountByAccountNumber(String accountNumber) {
		AccountDTO account = accountService.getAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Can't find Account With Number " + accountNumber);
		}
		menuContext.setCurrentAccount(account);
		return account;
	}
}
