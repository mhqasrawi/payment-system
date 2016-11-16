package com.progressoft.jip;

import java.util.Currency;

public class CurrencyField extends AbstractField<Currency> {

	@Override
	public void setValue(String value) {
		this.value = Currency.getInstance(value);
	}

}
