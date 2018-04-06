package com.yhj.chapter21.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEventGenerator extends IntGenerator {
    private int currentEvent = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int nextInt() {
        lock.lock();
        try {
            ++ currentEvent;
            ++ currentEvent;
            return currentEvent; // return 语句一定要在 try 里面，以确保 unlock 不会过早发生，而将数据暴露给第二个任务。
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EventChecker.test(new MutexEventGenerator());
    }
}
