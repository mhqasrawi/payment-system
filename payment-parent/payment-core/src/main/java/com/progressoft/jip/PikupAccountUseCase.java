package com.progressoft.jip;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class PikupAccountUseCase {

	private final AccountPersistenceService accountService;

	public PikupAccountUseCase(AccountPersistenceService accountService) {
		this.accountService = accountService;
	}

	public AccountDTO getAccountByAccountNumber(String accountNumber) {
		AccountDTO account = accountService.getAccount(accountNumber);
		if (account == null) {
			throw new RuntimeException("Can't find Account With Number " + accountNumber);
		}
		return account;
	}
}
