package com.progressoft.jip.test.ui.webrendering;
 
import com.progressoft.jip.ui.field.AbstractField;
import com.progressoft.jip.ui.field.Field;

public class FieldMock<T> implements Field<T> {
	private String name;
	private String description;
	private T value;
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public AbstractField<T> setValue(String value) {
		return null;
	}
}
