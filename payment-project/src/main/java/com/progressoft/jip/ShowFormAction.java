package com.progressoft.jip;

public class ShowFormAction implements Action {

	private Form form;

	public ShowFormAction(Form form) {
		this.form = form;
	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		menuContext.getMenuMenager().renderForm(form);
		menuContext.put(MenuContext.FORM_VALUE, form);
		return menuContext;

	}

}