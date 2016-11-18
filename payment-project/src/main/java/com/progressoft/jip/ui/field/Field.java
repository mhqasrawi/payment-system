package com.progressoft.jip.ui.field;

public interface Field<T> {

	String getName();

	T getValue();
	
	AbstractField<T> setValue(String value);

	String getDescription();

}