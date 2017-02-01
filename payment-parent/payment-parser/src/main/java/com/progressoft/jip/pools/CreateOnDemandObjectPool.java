package com.progressoft.jip.pools;

import java.util.ArrayList;
import java.util.List;

public abstract class CreateOnDemandObjectPool<T> implements ObjectPool<T> {
    private static final int DEFAULT_MAX_POOL_SIZE = 4;
    private final Object poolMonitor = new Object();

    private int maxPoolSize;
    private List<T> taken = new ArrayList<>();
    private int numberOfObjectsTaken = 0;

    protected List<T> pool = new ArrayList<>();

    public CreateOnDemandObjectPool() {
        maxPoolSize = DEFAULT_MAX_POOL_SIZE;
    }

    public CreateOnDemandObjectPool(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    protected abstract T createNewObject();

    protected abstract T removeAnyObjectFromPool();

    @Override
    public T get() {
        synchronized (poolMonitor) {
            while (numberOfObjectsTaken == maxPoolSize) {
                try {
                    poolMonitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            T t;
            if (pool.isEmpty() && numberOfObjectsTaken < maxPoolSize) {
                t = createNewObject();
            } else {
                t = removeAnyObjectFromPool();
            }

            taken.add(t);
            numberOfObjectsTaken++;
            return t;
        }
    }

    @Override
    public void put(T t) {
        synchronized (poolMonitor) {
            pool.add(t);
            taken.remove(t);
            numberOfObjectsTaken--;
            poolMonitor.notifyAll();
        }
    }

    @Override
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void waitForTakenObjectsToReturn() {
        synchronized (poolMonitor) {
            while (numberOfObjectsTaken > 0) {
                try {
                    poolMonitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
