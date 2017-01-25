package com.progressoft.jip;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.progressoft.jip.ui.xml.ActionGenerator;
import com.progressoft.jip.ui.xml.element.ActionXml;

public class SpringBasedActionGenerator implements ActionGenerator {

	private static final String INIT = "init";

	private static Logger logger = LoggerFactory.getLogger(SpringBasedActionGenerator.class);

	@Inject
	private ApplicationContext appContext;

	@Override
	public Object generateAction(ActionXml actionXml) {
		try {
			Object autowiredAction = generateConfiguredAction(actionXml);
			callInitMethodIfExist(autowiredAction);
			return autowiredAction;
		} catch (ClassNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			throw new ActionException(ex);
		}
	}

	public Object generateConfiguredAction(ActionXml actionXml) throws ClassNotFoundException {
		AutowireCapableBeanFactory autowireCapableBeanFactory = appContext.getAutowireCapableBeanFactory();
		Class<?> loadClass = this.getClass().getClassLoader().loadClass(actionXml.getActionClassName().trim());
		return autowireCapableBeanFactory.autowire(loadClass,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
	}

	private void callInitMethodIfExist(Object autowiredAction) {
		Method method = null;
		try {
			method = autowiredAction.getClass().getMethod(INIT);
			invokeInitMethod(autowiredAction, method);
		} catch (NoSuchMethodException | SecurityException e1) {
			logger.error(e1.getMessage(), e1);
		}

	}

	private void invokeInitMethod(Object autowiredAction, Method method) {
		try {
			method.invoke(autowiredAction);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.error("Error While Invoke Init Mehotd", e);
			throw new  ActionException(e);
		}
	}
}
