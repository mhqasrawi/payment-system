package com.progressoft.jip;

public interface Form {

	Iterable<Field<?>> getFields();
	
	String getDescription();
	
	void addField(Field<?> field);
}
