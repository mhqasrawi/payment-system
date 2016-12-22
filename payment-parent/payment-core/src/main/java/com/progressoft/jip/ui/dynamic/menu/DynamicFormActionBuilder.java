package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

public interface DynamicFormActionBuilder<C extends MenuContext, T> {

	DynamicFormActionBuilder<C, T> setDefaultObjectStrategy(DefaultValueProvider<C, T> defaultObjectProvider);

	DynamicFormActionBuilder<C, T> changeDefaultInvocationHandler(InvocationHandler invocationHandler);

	DynamicFormActionBuilder<C, T> setInterfaceType(Class<T> interfaceClass);

	DynamicFormActionBuilder<C, T> setForm(Form form);

	DynamicFormActionBuilder<C, T> setSubmitAction(SubmitAction<C, T> submitAction);

	Action<C> build();

}