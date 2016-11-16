package com.progressoft.jip;

public interface MenuContext {

	public static final String LAST_ANSWER_KEY = "LAST_ANSWER_KEY";
	public static final String MENU_KEY = "MENU_KEY";
	public static final String FORM_VALUE = "FormValue";

	void put(String key, Object value);

	<T> T get(String key);

}
