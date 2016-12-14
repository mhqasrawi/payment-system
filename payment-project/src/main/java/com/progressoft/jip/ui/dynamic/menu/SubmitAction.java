package com.progressoft.jip.ui.dynamic.menu;

import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface SubmitAction<C extends MenuContext, T> {

	void submitAction(C menuContext, T object);

}