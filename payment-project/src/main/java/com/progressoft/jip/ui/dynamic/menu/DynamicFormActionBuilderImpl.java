package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.action.ShowFormAction;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

/**
 * @author Ahmad.Jardat
 *
 */
public class DynamicFormActionBuilderImpl<C extends MenuContext, T>
		implements Action<C>, DynamicFormActionBuilder<C, T> {

	private Map<String, Object> values = new HashMap<>();
	private Form form;
	private Class<?> interfaceClass;
	private SubmitAction<C, T> submitAction;
	private Function<C, T> defaultObjectProvider;
	private InvocationHandler invocationHandler;

	public DynamicFormActionBuilderImpl(Form form, Class<?> interfaceClass, SubmitAction<C, T> defaultObjectProvider) {
		this.form = form;
		this.interfaceClass = interfaceClass;
		this.submitAction = defaultObjectProvider;
	}
	
	public DynamicFormActionBuilderImpl(){
		
	}

	@Override
	public DynamicFormActionBuilder<C, T> setDefaultObjectStrategy(Function<C, T> defaultObjectProvider) {
		this.defaultObjectProvider = defaultObjectProvider;
		return this;
	}

	@Override
	public DynamicFormActionBuilder<C, T> changeDefaultInvocationHandler(InvocationHandler invocationHandler) {
		this.invocationHandler = invocationHandler;
		return this;
	}

	@Override
	public DynamicFormActionBuilder<C, T> setInterfaceType(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
		return this;
	}

	@Override
	public DynamicFormActionBuilder<C, T> setForm(Form form) {
		this.form = form;
		return this;
	}

	@Override
	public DynamicFormActionBuilder<C, T> setSubmitAction(SubmitAction<C, T> submitAction) {
		this.submitAction = submitAction;
		return this;
	}

	@Override
	public Action<C> build() {
		return this;
	}

	@Override
	public C doAction(C menuContext) {
		showForm(menuContext);
		fillValuesFromForm();
		T newProxyInstance = generateDynamicWrapper(menuContext);
		submitAction.process(menuContext, newProxyInstance);
		return menuContext;
	}

	private T generateDynamicWrapper(C menuContext) {
		@SuppressWarnings("unchecked")
		T newProxyInstance = ((T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[] { interfaceClass }, getInvocationHandler(menuContext)));
		return newProxyInstance;
	}

	private InvocationHandler getInvocationHandler(C menuContext) {
		if (this.invocationHandler == null)
			this.invocationHandler = new DefaultInvocationHandler<C, T>(values, menuContext, defaultObjectProvider);
		return this.invocationHandler;
	}

	private void fillValuesFromForm() {
		for (Field<?> field : form.getAllFields()) {
			values.put(field.getName(), field.getValue());
		}
	}

	private void showForm(C menuContext) {
		(new ShowFormAction<C>(form)).doAction(menuContext);
	}

}
