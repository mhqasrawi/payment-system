package com.progressoft.jip.payment.account.service;

import java.util.ArrayList;
import java.util.List;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
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
		AccountDTO account = accountDAO.get(accountNumber);
		AccountDTOImpl accountDTO = getIBAN(account);
		return accountDTO;
	}

	public AccountDTO getById(String id) {
		AccountDTO account = accountDAO.getById(id);
		AccountDTOImpl accountDTO = getIBAN(account);
		return accountDTO;

	}

	public AccountDTO save(AccountDTO accountDTO) {
		saveIBAN(accountDTO);
		return this.accountDAO.save(accountDTO);
	}

	public Iterable<AccountDTO> getAll() {
		List<AccountDTO> accounts = new ArrayList<AccountDTO>();
		Iterable<AccountDTO> accountsDTO = this.accountDAO.getAll();

		for (AccountDTO account : accountsDTO) {
			accounts.add(getIBAN(account));
		}
		return accounts;
	}

	private AccountDTOImpl getIBAN(AccountDTO accountDTO2) {
		AccountDTOImpl accountDTO = new AccountDTOImpl(accountDTO2);
		IBANDTO ibanById = ibanServiceImpl.getIBANById(accountDTO.getAccountNumber());
		accountDTO.setIbandto(ibanById);
		return accountDTO;
	}

	private void saveIBAN(AccountDTO account) {
		ibanServiceImpl.save(account.getIban());
	}

}
