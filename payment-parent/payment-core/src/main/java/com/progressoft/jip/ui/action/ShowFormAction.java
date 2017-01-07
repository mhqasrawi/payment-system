package com.progressoft.jip.ui.action;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

public class ShowFormAction<C extends MenuContext,T> implements Action<C> {

	private Form<C,T> form;

	public ShowFormAction(Form<C,T> form) {
		this.form = form;
	}

	@Override
	public C doAction(C menuContext) {
		menuContext.getMenuMenager().renderForm(form);
		menuContext.put(MenuContext.FORM_VALUE, form);
		return menuContext;

	}

}