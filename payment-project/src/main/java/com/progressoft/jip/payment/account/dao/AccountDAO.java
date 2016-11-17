package com.progressoft.jip.payment.account.dao;

import com.progressoft.jip.payment.account.AccountDTO;

public interface AccountDAO {

	AccountDTO save(AccountDTO account);

	// void update(AccountDTO account);

	boolean delete(String accountNumber);

	AccountDTO get(String accountNumber);

	AccountDTO getById(String id);

	Iterable<AccountDTO> getAll();

	Iterable<AccountDTO> getAccountsByCustomerName(String customerName);

}
