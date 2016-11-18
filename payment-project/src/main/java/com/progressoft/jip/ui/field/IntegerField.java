package com.progressoft.jip.ui.field;

import com.progressoft.jip.payment.iban.IBANDTO;

public class IntegerField extends AbstractField<Integer> {

	@Override
	public AbstractField<Integer> setValue(String value) {
		this.value = Integer.valueOf(value);
		return this;
	}

}
