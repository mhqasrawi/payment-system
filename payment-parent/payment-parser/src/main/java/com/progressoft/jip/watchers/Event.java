package com.progressoft.jip.watchers;

@FunctionalInterface
public interface Event<T> {
    void handleEvent(T t);
}
