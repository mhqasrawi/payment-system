package com.progressoft.jip.payment.account;

import java.util.Currency;

import com.progressoft.jip.payment.iban.DTO;
import com.progressoft.jip.payment.iban.IBANDTO;

public interface AccountDTO extends DTO {

    String getAccountNumber();

    IBANDTO getIban();

    String getAccountName();

    Currency getCurreny();

    AccountStatus getAccountStatus();

    public enum AccountStatus {

	ACTIVE(0), INACTIVE(1);

	private int index;

	private AccountStatus(int index) {
	    this.index = index;
	}

	public int getIndex() {
	    return index;
	}

	public AccountStatus getAccountStatus(int index) {
	    for (AccountStatus accountStatus : values()) {
		if (accountStatus.getIndex() == index) {
		    return accountStatus;
		}
	    }
	    return null;
	}
    }

}
