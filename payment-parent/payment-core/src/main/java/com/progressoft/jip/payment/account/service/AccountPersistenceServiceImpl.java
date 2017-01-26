package com.progressoft.jip.payment.account.service;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AccountPersistenceServiceImpl implements AccountPersistenceService {

    private AccountDAO accountDAO;
    private IBANPersistenceService ibanServiceImpl;

    @Inject
    public AccountPersistenceServiceImpl(AccountDAO accountDAO, IBANPersistenceService ibanService) {
        this.accountDAO = accountDAO;
        this.ibanServiceImpl = ibanService;
    }

    public AccountDTO save(AccountDTO accountDTO) {
        IBANDTO saveIBAN = saveIBAN(accountDTO.getIban());
        AccountDTOImpl accountDTOImpl = new AccountDTOImpl(accountDTO);
        accountDTOImpl.setIbandto(saveIBAN);
        return this.accountDAO.save(accountDTOImpl);
    }

    public AccountDTO getAccount(String accountNumber) {
        AccountDTO account = accountDAO.get(accountNumber);
        return getIBAN(account);
    }

    public AccountDTO getById(int id) {
        AccountDTO account = accountDAO.getById(id);
        return getIBAN(account);

    }

    public Iterable<AccountDTO> getAll() {
        List<AccountDTO> accounts = new ArrayList<>();
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
        accountDTOImpl.setIbandto(accountIBAN);
        return (AccountDTOImpl) accountDTOImpl;
    }

    private IBANDTO saveIBAN(IBANDTO ibandto) {
        return ibanServiceImpl.save(ibandto);
    }

}
