package com.progressoft.jip.dependency;

import org.springframework.context.ApplicationContext;

public class SpringImplementationProvider implements ImplementationProvider {
	
	private final ApplicationContext applicationContext;

	public SpringImplementationProvider(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public <T> T getImplementation(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

}
