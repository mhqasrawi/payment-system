package com.progressoft.jip.ui.dynamic.menu;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.Menu;
import com.progressoft.jip.ui.menu.MenuContext;

public class FormToObjectBuilderImpl<C extends MenuContext, T> implements FormToObjectBuilder<C, T> {

	private String description;
	private Form form;
	private Class<?> interfaceClass;
	private SubmitAction<C, T> objectProssingStratege;
	private Function<C, T> defaultObjectProvider;
	private List<Menu<C>> subMenu = Collections.emptyList();

	@Override
	public FormToObjectBuilder<C, T> setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public FormToObjectBuilder<C, T> setForm(Form form) {
		this.form = form;
		return this;
	}

	@Override
	public FormToObjectBuilder<C, T> setInterfaceType(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
		return this;
	}

	@Override
	public FormToObjectBuilder<C, T> setProcessingStrategy(SubmitAction<C, T> objectProssingStratege) {
		this.objectProssingStratege = objectProssingStratege;
		return this;
	}

	@Override
	public FormToObjectBuilder<C, T> setSubMenu(List<Menu<C>> subMenu) {
		this.subMenu = subMenu;
		return this;
	}

	protected DynamicFormMenu<C, T> buildNewMenu() {
		if (defaultObjectProvider == null) {
			return new DynamicFormMenu<C, T>(description, subMenu, form, interfaceClass, objectProssingStratege);
		} else {
			return new DynamicFormMenu<C, T>(description, subMenu, form, interfaceClass, objectProssingStratege,
					defaultObjectProvider);
		}
	}

	@Override
	public DynamicFormMenu<C, T> build() {
		return buildNewMenu();
	}

	@Override
	public DynamicFormMenu<C, T> buildEditMenu(Function<C, T> defaultObjectProvider) {
		this.defaultObjectProvider = defaultObjectProvider;
		return buildNewMenu();
	}
}
