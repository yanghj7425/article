package com.yhj.chapter21.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable {
    private int i = 0;


    public int getValue() {
        return i;
    }

    private synchronized void envnIncrement() {
        i++; i++;
    }

    @Override
    public void run() {
        while (true) {
            envnIncrement();
        }
    }
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        AtomicityTest ta = new AtomicityTest();
        service.execute(ta);
        while (true) {
            int val = ta.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
                break;
            }
        }
        System.exit(0);
    }
}
