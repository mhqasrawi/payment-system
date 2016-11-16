package com.progressoft.jip.payment.account;

import java.util.Currency;

import com.progressoft.jip.payment.iban.IBANDTO;

public class AccountDTOImpl implements AccountDTO {

	private long id;
	private String accountNumber;
	private IBANDTO ibandto;
	private String accountName;
	private Currency currency;
	private AccountStatus accountStatus;

	public AccountDTOImpl() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setIbandto(IBANDTO ibandto) {
		this.ibandto = ibandto;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public long getId() {
		return this.id;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public IBANDTO getIban() {
		return this.ibandto;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public Currency getCurreny() {
		return this.currency;
	}

	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

}
