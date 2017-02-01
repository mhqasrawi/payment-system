package com.progressoft.jip.pools;

public abstract class AutoReturnableThread extends Thread {
    private final Object startMonitor = new Object();
    private boolean suspended = true;

    protected abstract void doOperation();

    protected abstract void releaseThread();

    @Override
    public void run() {
        do {
            waitUntilNotified();
            doOperation();
            suspendAndRelease();
        } while (!isInterrupted());
    }

    protected void notifyThread() {
        synchronized (startMonitor) {
            suspended = false;
            startMonitor.notify();
        }
    }

    private void waitUntilNotified() {
        synchronized (startMonitor) {
            while (suspended) {
                try {
                    startMonitor.wait();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    private void suspendAndRelease() {
        synchronized (startMonitor) {
            suspended = true;
        }
        releaseThread();
    }
}
