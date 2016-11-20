package com.progressoft.jip.ui.dynamic.menu;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

public class FormToObjectBuilderImpl<MENU_CONTEXT extends MenuContext, INTERFACE_TYPE>
		implements FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> {

	private String description;
	private Form form;
	private Class<?> interfaceClass;
	private ObjectProcessingStrategy<MENU_CONTEXT,INTERFACE_TYPE> objectProssingStratege;
	private Function<MENU_CONTEXT,INTERFACE_TYPE>  defaultObjectProvider;
	private List<Menu<MENU_CONTEXT>> subMenu = Collections.emptyList();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.progressoft.jip.ui.dynamic.menu.IFormToObjectBuilder#setDescription(
	 * java.lang.String)
	 */
	@Override
	public FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setDescription(String description) {
		this.description = description;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.progressoft.jip.ui.dynamic.menu.IFormToObjectBuilder#setForm(com.
	 * progressoft.jip.ui.form.Form)
	 */
	@Override
	public FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setForm(Form form) {
		this.form = form;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.progressoft.jip.ui.dynamic.menu.IFormToObjectBuilder#setInterfaceType
	 * (java.lang.Class)
	 */
	@Override
	public FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setInterfaceType(Class<INTERFACE_TYPE> interfaceClass) {
		this.interfaceClass = interfaceClass;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.progressoft.jip.ui.dynamic.menu.IFormToObjectBuilder#
	 * setProcessingStrategy(java.util.function.Consumer)
	 */
	@Override
	public FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setProcessingStrategy(
			ObjectProcessingStrategy<MENU_CONTEXT,INTERFACE_TYPE> objectProssingStratege) {
		this.objectProssingStratege = objectProssingStratege;
		return this;
	}
	
	
	@Override
	public FormToObjectBuilder<MENU_CONTEXT, INTERFACE_TYPE> setSubMenu(List<Menu<MENU_CONTEXT>> subMenu) {
		this.subMenu  = subMenu;
		return this;
	}

	protected FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE> buildNewMenu() {
		if (defaultObjectProvider == null) {
			return new FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE>(description,subMenu, form, interfaceClass,
					objectProssingStratege);
		}else{
			return new FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE>(description,subMenu, form, interfaceClass,
					objectProssingStratege,defaultObjectProvider);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.progressoft.jip.ui.dynamic.menu.IFormToObjectBuilder#build()
	 */
	@Override
	public FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE> build() {
		return buildNewMenu();
	}

	@Override
	public FormToObjectMenu<MENU_CONTEXT, INTERFACE_TYPE> buildEditMenu(Function<MENU_CONTEXT,INTERFACE_TYPE>  defaultObjectProvider) {
		this.defaultObjectProvider = defaultObjectProvider;
		return buildNewMenu();
	}

	

}
