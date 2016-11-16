package com.progressoft.jip.payment.account.service;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;

public class AccountPersistenceServiceImpl implements AccountPersistenceService {

	private AccountDAO accountDAO;
	private IBANPersistenceService ibanServiceImpl;

	public AccountPersistenceServiceImpl(AccountDAO accountDAO, IBANDAO ibandao) {
		this.accountDAO = accountDAO;
		this.ibanServiceImpl = new IBANPersistenceServiceImpl(ibandao);

	}

	public AccountDTO getAccount(String accountNumber) {

		return this.accountDAO.get(accountNumber, ibanServiceImpl);
	}

	public AccountDTO getById(String id) {
		return this.accountDAO.getById(id, ibanServiceImpl);

	}

	public AccountDTO save(AccountDTO accountDTO) {

		saveIBAN(accountDTO);
		return this.accountDAO.save(accountDTO);
	}

	public Iterable<AccountDTO> getAll() {
		return this.accountDAO.getAll(ibanServiceImpl);
	}

	private void saveIBAN(AccountDTO account) {
		ibanServiceImpl.save(account.getIban());
	}

}
