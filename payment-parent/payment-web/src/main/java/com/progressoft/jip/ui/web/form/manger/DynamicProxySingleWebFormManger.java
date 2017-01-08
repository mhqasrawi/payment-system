package com.progressoft.jip.ui.web.form.manger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.progressoft.jip.ui.dynamic.menu.DefaultInvocationHandler;
import com.progressoft.jip.ui.dynamic.menu.DefaultValueProvider;
import com.progressoft.jip.ui.field.Field;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.menu.MenuContext;

public class DynamicProxySingleWebFormManger<C extends MenuContext, T> implements SingleWebFormManger<C, T> {

	private final Form<C, T> form;
	private final Map<String, Object> values = new HashMap<>();
	
	private InvocationHandler invocationHandler;

	public DynamicProxySingleWebFormManger(Form<C, T> form) {
		this.form = form;
	}

	@Override
	public void submitFieldValue(String fieldName, String value) {
		Field<?> field = form.getFieldByName(fieldName);
		if (Objects.isNull(field))
			throw new NoSuchFieldException("no field with name " + fieldName);
		field.setValue(value);
	}

	@Override
	public void submitAction(C menuContext) {
		fillValuesFromForm();
		T newProxyInstance = generateDynamicWrapper(menuContext);
		form.getSubmitAction().submitAction(menuContext, newProxyInstance);
	}

	private void fillValuesFromForm() {
		for (Field<?> field : form.getAllFields()) {
			values.put(field.getName(), field.getValue());
		}
	}

	private T generateDynamicWrapper(C menuContext) {
		@SuppressWarnings("unchecked")
		T newProxyInstance = ((T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[] { form.getClassType() }, getInvocationHandler(menuContext)));
		return newProxyInstance;
	}

	private InvocationHandler getInvocationHandler(C menuContext) {
		if (this.invocationHandler == null)
			this.invocationHandler = new DefaultInvocationHandler<C, T>(values, menuContext,
					(form instanceof DefaultValueProvider) ? (DefaultValueProvider<C, T>) form : null);
		return this.invocationHandler;
	}
}
