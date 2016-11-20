package com.progressoft.jip.ui.form;

import com.progressoft.jip.ui.field.Field;

public interface Form {

	Iterable<Field<?>> getFields();

	String getDescription();
	
	Iterable<Field<?>> getAllFields();
	

	default Field<?> getFieldByName(String fieldName) {
		for (Field<?> field : getFields()) {
			if (field.getName().equals(fieldName))
				return field;
		}
		return null;
	}

}