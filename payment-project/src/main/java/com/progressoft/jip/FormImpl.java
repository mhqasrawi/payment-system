package com.progressoft.jip;

import java.util.ArrayList;
import java.util.List;

public class FormImpl implements Form {

	private List<Field<?>> fields = new ArrayList<>();
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

	public void addField(Field<?> field) {
		fields.add(field);
	}
}
