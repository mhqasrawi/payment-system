package com.progressoft.jip.ui.web.form.manger;

import com.progressoft.jip.ui.menu.MenuContext;

public interface WebFormManger<C extends MenuContext, T> {

	void submitFieldValue(String fieldName, String value);

	void submitAction(C menuContext);

}