package com.progressoft.jip.ui.field;

import java.util.Currency;

public class CurrencyField extends AbstractField<Currency> {

	@Override
	public AbstractField<Currency> setValue(String value) {
		this.value = Currency.getInstance(value);
		return this;
	}

}
