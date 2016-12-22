package com.progressoft.jip.ui.field;

public interface Field<T> {

	String getName();

	String getDescription();

	T getValue();
	
	AbstractField<T> setValue(String value);

}