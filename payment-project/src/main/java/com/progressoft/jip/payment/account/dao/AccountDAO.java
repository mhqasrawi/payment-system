package com.progressoft.jip.payment.account.dao;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;

public interface AccountDAO {

	AccountDTO save(AccountDTO account);

	//void update(AccountDTO account);

	boolean delete(String accountNumber);

	AccountDTO get(String accountNumber,IBANPersistenceService ibanPersistenceServiceImpl);

	AccountDTO getById(String id,IBANPersistenceService ibanPersistenceServiceImpl);

	Iterable<AccountDTO> getAll(IBANPersistenceService ibanPersistenceServiceImpl);

}
