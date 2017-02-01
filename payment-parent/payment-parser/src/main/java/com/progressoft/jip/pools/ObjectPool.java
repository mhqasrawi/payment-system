package com.progressoft.jip.pools;

public interface ObjectPool<T> {
    int getMaxPoolSize();

    T get();

    void put(T object);
}
