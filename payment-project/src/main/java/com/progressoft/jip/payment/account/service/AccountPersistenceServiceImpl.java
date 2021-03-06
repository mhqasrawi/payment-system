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

	public AccountDTO save(AccountDTO accountDTO) {
		System.out.println("accountDTO IBAN: " + (accountDTO.getIban() == null));

		IBANDTO saveIBAN = saveIBAN(accountDTO.getIban());
		AccountDTOImpl accountDTOImpl = new AccountDTOImpl(accountDTO);
		accountDTOImpl.setIbandto(saveIBAN);
		return this.accountDAO.save(accountDTOImpl);
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

	public Iterable<AccountDTO> getAll() {
		List<AccountDTO> accounts = new ArrayList<AccountDTO>();
		Iterable<AccountDTO> accountsDTO = this.accountDAO.getAll();

		for (AccountDTO account : accountsDTO) {
			accounts.add(getIBAN(account));
		}
		return accounts;
	}

	private AccountDTOImpl getIBAN(AccountDTO account) {
		AccountDTO accountDTO = accountDAO.get(account.getAccountNumber());

		AccountDTOImpl accountDTOImpl = new AccountDTOImpl(accountDTO);

		IBANDTO accountIBAN = ibanServiceImpl.getIBANById(accountDTO.getIbanId());
		System.out.println("getIBAN: " + accountIBAN.getId());
		accountDTOImpl.setIbandto(accountIBAN);
		return (AccountDTOImpl) accountDTOImpl;
	}

	private IBANDTO saveIBAN(IBANDTO ibandto) {
		ibandto = ibanServiceImpl.save(ibandto);
		return ibandto;
	}

}
