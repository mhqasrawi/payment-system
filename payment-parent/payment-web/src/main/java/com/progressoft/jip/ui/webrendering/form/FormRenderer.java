package com.progressoft.jip.ui.webrendering.form;

import com.progressoft.jip.ui.form.Form;

@FunctionalInterface
public interface FormRenderer {
	String renderToHtml(Form form);
}
