package com.progressoft.jip;

public interface Field<T> {

	String getName();

	T getValue();
	
	void setValue(String value);

	String getDescription();

}