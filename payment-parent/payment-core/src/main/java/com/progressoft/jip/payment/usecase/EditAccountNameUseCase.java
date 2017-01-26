package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;

public class EditAccountNameUseCase {

    private final AccountPersistenceService accountService;

    public EditAccountNameUseCase(AccountPersistenceService accountService) {
        this.accountService = accountService;
    }

    public void process(PaymentMenuContext menuContext, AccountDTO accountDTO) {
        menuContext.setCurrentAccount(accountService.save(accountDTO));
    }

}
