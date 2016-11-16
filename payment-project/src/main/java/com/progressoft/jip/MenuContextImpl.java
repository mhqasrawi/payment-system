package com.progressoft.jip;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MenuContextImpl implements MenuContext {

	private Map<String, Object> values = new HashMap<String, Object>();
	private Deque<Menu> menuStack = new LinkedList<>();

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

	@Override
	public void pushMenuStack(Menu menu) {
		menuStack.push(menu);
	}

	@Override
	public Menu popMenuStack() {
		return menuStack.pop();
	}

}
