package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

public class ShowFormAction<C extends MenuContext> implements Action<C> {

	private Form form;

	public ShowFormAction(Form form) {
		this.form = form;
	}

	@Override
	public C doAction(C menuContext) {
		menuContext.getMenuMenager().renderForm(form);
		menuContext.put(MenuContext.FORM_VALUE, form);
		return menuContext;

	}

}