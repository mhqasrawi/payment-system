package com.progressoft.jip.payment.account.service;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;
import com.progressoft.jip.payment.iban.service.impl.IBANPersistenceServiceImpl;

public class AccountPersistenceServiceImpl implements AccountPersistenceService {

	private AccountDAO accountDAO;
	private IBANDAO ibandao;

	public AccountPersistenceServiceImpl(AccountDAO accountDAO, IBANDAO ibandao) {
		this.accountDAO = accountDAO;
		this.ibandao = ibandao;
	}

	public AccountDTO getAccount(String accountNumber) {
		return this.accountDAO.get(accountNumber);
	}

	public AccountDTO getById(String id) {
		return this.accountDAO.getById(id);
	}

	public AccountDTO save(AccountDTO accountDTO) {

		saveIBAN(accountDTO, ibandao);
		return this.accountDAO.save(accountDTO);
	}

	public Iterable<AccountDTO> getAll() {
		return this.accountDAO.getAll();
	}

	private void saveIBAN(AccountDTO account, IBANDAO ibandao) {
		IBANPersistenceService ibanServiceImpl = new IBANPersistenceServiceImpl(ibandao);
		ibanServiceImpl.save(account.getIban());
	}
}
