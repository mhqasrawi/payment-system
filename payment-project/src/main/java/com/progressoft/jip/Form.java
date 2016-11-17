package com.progressoft.jip;

public interface Form {

	Iterable<Field<?>> getFields();

	String getDescription();

	default Field<?> getFieldByName(String fieldName) {
		for (Field<?> field : getFields()) {
			if (field.getName().equals(fieldName))
				return field;
		}
		return null;
	}

}