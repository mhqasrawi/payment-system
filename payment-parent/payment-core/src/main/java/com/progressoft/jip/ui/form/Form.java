package com.progressoft.jip.ui.form;

import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.menu.MenuContext;

public interface Form<C extends MenuContext, T> {

	Iterable<Field<?>> getFields();

	String getDescription();

	Iterable<Field<?>> getAllFields();

	SubmitAction<C, T> getSubmitAction();

	Class<T> getClassType();
	
	default Field<?> getFieldByName(String fieldName) {
		for (Field<?> field : getFields()) {
			if (field.getName().equals(fieldName))
				return field;
		}
		return null;
	}

}