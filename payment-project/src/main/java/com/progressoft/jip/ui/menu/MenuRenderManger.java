package com.progressoft.jip.ui.menu;

import com.progressoft.jip.ui.form.Form;

public interface MenuRenderManger<C extends MenuContext> {

	void renderMenu(Menu<C> menu);

	void renderForm(Form form);
}