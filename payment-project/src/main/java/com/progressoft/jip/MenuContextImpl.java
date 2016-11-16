package com.progressoft.jip;

import java.util.HashMap;
import java.util.Map;

public class MenuContextImpl implements MenuContext {

	private Map<String, Object> values = new HashMap<String, Object>();

	public void put(String key, Object value) {
		values.put(key, value);
	}

	public <T> T get(String key) {
		Object value = values.get(key);
		if (value == null) {
			throw new KeyWithoutValueException(String.format("Key %s have null value", key));
		}
		return (T) value;
	}

}
