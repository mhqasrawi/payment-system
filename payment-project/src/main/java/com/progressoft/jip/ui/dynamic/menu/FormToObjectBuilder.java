package com.progressoft.jip.ui.dynamic.menu;


import java.util.List;
import java.util.function.Function;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

public interface FormToObjectBuilder<MENU_CONTEXT extends MenuContext, INTERFACE_TYPE> {

	FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setDescription(String description);

	FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setForm(Form form);

	FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setInterfaceType(Class<INTERFACE_TYPE> interfaceClass);

	FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setProcessingStrategy(
			ObjectProcessingStrategy<MENU_CONTEXT,INTERFACE_TYPE> objectProssingStratege);

	FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setSubMenu(List<Menu<MENU_CONTEXT>> subMenu);
	
	FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE> build();

	FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE> buildEditMenu(Function<MENU_CONTEXT,INTERFACE_TYPE>  interface_TYPE);

}