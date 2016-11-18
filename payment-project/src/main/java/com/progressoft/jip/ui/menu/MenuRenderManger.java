package com.progressoft.jip.ui.menu;

import com.progressoft.jip.ui.form.Form;

public interface MenuRenderManger<T extends MenuContext> {

	void renderMenu(Menu<T> menu);

	void renderForm(Form form);
}