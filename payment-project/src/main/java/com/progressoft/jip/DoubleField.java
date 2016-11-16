package com.progressoft.jip;

public class DoubleField extends AbstractField<Double> {

	@Override
	public void setValue(String value) {
		this.value = Double.valueOf(value);
	}

}
