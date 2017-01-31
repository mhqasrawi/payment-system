package com.progressoft.jip.ui.dynamic.menu;

/**
 * @author u612
 */
@FunctionalInterface
public interface DefaultValueProvider<T, R> {

    R defaultValue(T t);

}

