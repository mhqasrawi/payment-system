package com.progressoft.jip.ui.dynamic.menu;

import java.util.List;
import java.util.function.Function;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;

public class DynamicFormMenu<C extends MenuContext, T> extends MenuImpl<C> {

	DynamicFormMenu(String description, List<Menu<C>> subMenu, Form form, Class<?> interfaceClass,
			SubmitAction<C, T> submitAction) {
		super(description, subMenu, new DynamicFormActionBuilderImpl<C, T>(form, interfaceClass, submitAction));
	}

	DynamicFormMenu(String description, List<Menu<C>> subMenu, Form form, Class<?> interfaceClass,
			SubmitAction<C, T> submitAction, Function<C, T> defaultObjectProvider) {
		super(description, subMenu, new DynamicFormActionBuilderImpl<C, T>(form, interfaceClass, submitAction)
				.setDefaultObjectStrategy(defaultObjectProvider).build());
	}

	}
