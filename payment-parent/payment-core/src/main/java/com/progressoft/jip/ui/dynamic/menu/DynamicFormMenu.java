package com.progressoft.jip.ui.dynamic.menu;

import java.util.List;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;
import com.progressoft.jip.ui.menu.MenuImpl;

public class DynamicFormMenu<C extends MenuContext, T> extends MenuImpl<C> {

	DynamicFormMenu(String description, List<Menu<C>> subMenu, Form<C,T> form, Class<?> interfaceClass,
			SubmitAction<C, T> submitAction) {
		super(description, subMenu, new DynamicFormActionBuilderImpl<C, T>(form, interfaceClass, submitAction));
	}

	DynamicFormMenu(String description, List<Menu<C>> subMenu, Form<C,T> form, Class<?> interfaceClass,
			SubmitAction<C, T> submitAction, DefaultValueProvider<C, T> defaultObjectProvider) {
		super(description, subMenu, new DynamicFormActionBuilderImpl<C, T>(form, interfaceClass, submitAction)
				.setDefaultObjectStrategy(defaultObjectProvider).build());
	}

	}
