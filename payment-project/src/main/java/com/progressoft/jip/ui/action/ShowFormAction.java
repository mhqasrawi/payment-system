package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

public class ShowFormAction<T extends MenuContext> implements Action<T> {

	private Form form;

	public ShowFormAction(Form form) {
		this.form = form;
	}

	@Override
	public T doAction(T menuContext) {
		menuContext.getMenuMenager().renderForm(form);
		menuContext.put(MenuContext.FORM_VALUE, form);
		return menuContext;

	}

}