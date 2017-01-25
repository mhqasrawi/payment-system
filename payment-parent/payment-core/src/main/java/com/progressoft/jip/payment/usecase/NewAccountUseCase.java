package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class NewAccountUseCase {

	private  AccountPersistenceService accountService;

	public void setAccountService(AccountPersistenceService accountService) {
		this.accountService = accountService;
	}

	public NewAccountUseCase(AccountPersistenceService accountService) {
		this.accountService = accountService;
	}

	public void process(PaymentMenuContext menuContext, AccountDTO accountDto) {
		menuContext.setCurrentAccount(accountService.save(accountDto));
	}
}
