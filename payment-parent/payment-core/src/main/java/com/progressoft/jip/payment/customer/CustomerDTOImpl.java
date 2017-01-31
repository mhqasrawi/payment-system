package com.progressoft.jip.payment.customer;

import com.progressoft.jip.payment.account.AccountDTO;

public class CustomerDTOImpl implements CustomerDTO {

    private String name;
    private Iterable<AccountDTO> accounts;
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<AccountDTO> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Iterable<AccountDTO> accounts) {
        this.accounts = accounts;
    }

}
