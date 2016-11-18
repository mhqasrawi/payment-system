package com.progressoft.jip.ui.field;

public class StringField extends AbstractField<String> {

	@Override
	public AbstractField<String> setValue(String value) {
		this.value = value;
		return this;
	}

}
