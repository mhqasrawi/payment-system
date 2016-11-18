package com.progressoft.jip.ui.form;

import java.util.ArrayList;
import java.util.List;

import com.progressoft.jip.ui.field.Field;

public class FormImpl implements Form {

	private List<Field<?>> fields = new ArrayList<>();
	private List<Field<?>> hiddenFields = new ArrayList<>();
	private String description = "";

	public FormImpl(String description) {
		this.description = description;
	}

	@Override
	public Iterable<Field<?>> getFields() {
		return fields;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public FormImpl addField(Field<?> field) {
		fields.add(field);
		return this;
	}
	
	public FormImpl addHiddenField(Field<?> field) {
		hiddenFields.add(field);
		return this;
	}

	@Override
	public Iterable<Field<?>> getAllFields() {
		List<Field<?>> allFields = new ArrayList<>();
		allFields.addAll(fields);
		allFields.addAll(hiddenFields);
		return allFields;
	}
}
