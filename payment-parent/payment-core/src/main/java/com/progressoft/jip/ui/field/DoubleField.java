package com.progressoft.jip.ui.field;

public class DoubleField extends AbstractField<Double> {

	@Override
	public AbstractField<Double> setValue(String value) {
		this.value = Double.valueOf(value);
		return this;
	}

}
