package com.progressoft.jip.ui.webrendering.form;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface FormRenderer<C extends MenuContext, T> {
	
	String renderToHtml(Form<C, T> form);

}
