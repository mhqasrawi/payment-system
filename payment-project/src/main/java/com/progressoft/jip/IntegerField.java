package com.progressoft.jip;

public class IntegerField extends AbstractField<Integer> {

	@Override
	public void setValue(String value) {
		this.value = Integer.valueOf(value);
	}

}
