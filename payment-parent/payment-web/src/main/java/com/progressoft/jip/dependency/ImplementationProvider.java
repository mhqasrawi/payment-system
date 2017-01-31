package com.progressoft.jip.dependency;

public interface ImplementationProvider {

    public static final String DEPENDENCY_PROVIDER = "dependency-provider";

    <T> T getImplementation(Class<T> clazz);

    void injectObjectDependency(Object object);
}
