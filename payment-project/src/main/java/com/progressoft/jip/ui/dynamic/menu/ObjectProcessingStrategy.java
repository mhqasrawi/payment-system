package com.progressoft.jip.ui.dynamic.menu;

import com.progressoft.jip.ui.menu.MenuContext;

@FunctionalInterface
public interface ObjectProcessingStrategy<MENU_CONTEXT extends MenuContext, INTERFACE_TYPE> {

	void process(MENU_CONTEXT menuContext, INTERFACE_TYPE object);

}
