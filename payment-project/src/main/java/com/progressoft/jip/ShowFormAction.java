package com.progressoft.jip;

public class ShowFormAction implements Action {

	private MenuRenderManger renderManger;
	private Form form;

	public ShowFormAction(MenuRenderManger renderManger,Form form) {
		this.renderManger = renderManger;
		this.form = form;
	}

	@Override
	public MenuContext doAction(MenuContext menuContext) {
		renderManger.renderForm(form);
		menuContext.put(MenuContext.FORM_VALUE, form);
		return menuContext;

	}

}