package com.progressoft.jip.payment.account.service;

import com.progressoft.jip.payment.account.AccountDTO;

public interface AccountPersistenceService {

	AccountDTO getAccount(String accountNumber);

	AccountDTO getById(int id);

	AccountDTO save(AccountDTO accountDTO);

	Iterable<AccountDTO> getAll();
}
