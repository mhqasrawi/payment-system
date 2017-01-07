package com.progressoft.jip.ui.dynamic.menu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

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

	private static final Logger logger = LoggerFactory.getLogger(DynamicFormActionBuilderImpl.class);
	private Map<String, Object> values = new HashMap<>();
	private Form<C, T> form;
	private Class<?> interfaceClass;
	private SubmitAction<C, T> submitAction;
	private DefaultValueProvider<C, T> defaultObjectProvider;
	private InvocationHandler invocationHandler;

	@Inject
	private ApplicationContext applicationContext;

	public DynamicFormActionBuilderImpl(Form<C, T> form, Class<?> interfaceClass,
			SubmitAction<C, T> defaultObjectProvider) {
		this.form = form;
		this.interfaceClass = interfaceClass;
		this.submitAction = defaultObjectProvider;
	}

	public DynamicFormActionBuilderImpl() {

	}

	@Override
	public DynamicFormActionBuilder<C, T> setDefaultObjectStrategy(DefaultValueProvider<C, T> defaultObjectProvider) {
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
	public DynamicFormActionBuilder<C, T> setForm(Form<C, T> form) {
		this.form = form;
		if (form instanceof SubmitAction) {
			if (Objects.nonNull(applicationContext))
				applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this.form,
						AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		}
		try {
			form.getClass().getMethod("init").invoke(form);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			return this;

		}
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
		submitAction.submitAction(menuContext, newProxyInstance);
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
		(new ShowFormAction<C, T>(form)).doAction(menuContext);
	}

	@Override
	public DynamicFormActionBuilder<C, T> refresh() {
		values = new HashMap<>();
		form = null;
		interfaceClass = null;
		submitAction = null;
		defaultObjectProvider = null;
		invocationHandler = null;

		return this;
	}

}
