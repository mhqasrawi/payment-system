package com.progressoft.jip.ui.field;

import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;

public class AccountStatusField extends AbstractField<AccountStatus> {

	@Override
	public AbstractField<AccountStatus> setValue(String value) {
		this.value = AccountStatus.valueOf(value);
		return this;
	}

}
