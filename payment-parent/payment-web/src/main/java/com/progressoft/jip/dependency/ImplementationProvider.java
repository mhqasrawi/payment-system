package com.progressoft.jip.dependency;

@FunctionalInterface
public interface ImplementationProvider {
	
	public static final String DEPENDENCY_PROVIDER = "dependency-provider";

	<T>  T getImplementation(Class<T> clazz);
	
}
