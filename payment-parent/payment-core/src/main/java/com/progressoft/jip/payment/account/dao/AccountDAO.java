package com.progressoft.jip.payment.account.dao;

import com.progressoft.jip.payment.account.AccountDTO;

public interface AccountDAO {

    AccountDTO save(AccountDTO account);

    boolean delete(String accountNumber);

    AccountDTO get(String accountNumber);

    AccountDTO getById(int id);

    Iterable<AccountDTO> getAll();

    Iterable<AccountDTO> getAccountsByCustomerName(String customerName);

}
