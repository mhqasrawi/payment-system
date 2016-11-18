package com.progressoft.jip.ui.menu;

public interface MenuContext {
	
	public static final String FORM_VALUE = "FormValue";
	public static final String MENU_RENDER_MANGER = "MENU_RENDER_MANGER";
	 

	void put(String key, Object value);

	<T> T get(String key);

	void pushMenuStack(Menu menu);

	@SuppressWarnings("rawtypes")
	Menu popMenuStack();
	
	@SuppressWarnings("rawtypes")
	default MenuRenderManger getMenuMenager(){
		return get(MENU_RENDER_MANGER);
	}
	
}
