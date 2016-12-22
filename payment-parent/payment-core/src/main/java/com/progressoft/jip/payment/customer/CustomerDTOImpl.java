package com.progressoft.jip.payment.customer;

import com.progressoft.jip.payment.account.AccountDTO;

public class CustomerDTOImpl implements CustomerDTO {

	private String name;
	private Iterable<AccountDTO> accounts;
	private int id;

	public void setName(String name) {
		this.name = name;
	}

	public void setAccounts(Iterable<AccountDTO> accounts) {
		this.accounts = accounts;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Iterable<AccountDTO> getAccounts() {
		return this.accounts;
	}

}