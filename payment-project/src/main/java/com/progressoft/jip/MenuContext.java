package com.progressoft.jip;

public interface MenuContext {

	public static final String MENU_RENDER_MANGER = "MENU_RENDER_MANGER";
	public static final String FORM_VALUE = "FormValue";
	public static final String ACCOUNT_DTO = "ACCOUNT_DTO"; 

	void put(String key, Object value);

	<T> T get(String key);

	void pushMenuStack(Menu menu);

	Menu popMenuStack();
	
	default MenuRenderManger getMenuMenager(){
		return get(MENU_RENDER_MANGER);
	}
	
}
