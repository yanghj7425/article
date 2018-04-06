package com.yhj.chapter21.concurrency;

public abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int nextInt();
    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
