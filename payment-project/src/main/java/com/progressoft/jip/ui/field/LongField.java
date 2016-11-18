package com.progressoft.jip.ui.field;

public class LongField extends AbstractField<Long> {

	@Override
	public AbstractField<Long> setValue(String value) {
		this.value = Long.getLong(value);
		return this;
	}

}
