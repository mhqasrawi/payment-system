package com.progressoft.jip.ui.dynamic.menu;


import java.util.List;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

public interface FormToObjectBuilder<C extends MenuContext, T> {

	FormToObjectBuilder<C, T> setDescription(String description);

	FormToObjectBuilder<C, T> setForm(Form form);

	FormToObjectBuilder<C, T> setInterfaceType(Class<T> interfaceClass);

	FormToObjectBuilder<C, T> setProcessingStrategy(
			SubmitAction<C,T> objectProssingStratege);

	FormToObjectBuilder<C, T> setSubMenu(List<Menu<C>> subMenu);
	
	DynamicFormMenu<C, T> build();

	DynamicFormMenu<C, T> buildEditMenu(DefaultValueProvider<C,T>  interface_TYPE);

}