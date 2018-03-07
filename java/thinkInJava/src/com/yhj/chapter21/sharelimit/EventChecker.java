package com.yhj.chapter21.sharelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    public EventChecker(IntGenerator intGenerator, int id) {
        this.generator = intGenerator;
        this.id = id;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {

            int val = generator.next();
            System.out.println( val );
            if (val % 2 != 0) {
                System.out.println(val + " not even !");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator gp, int count) {
        System.out.println("press control-c to exit!");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            exec.execute(new EventChecker(gp, i));
        }
        exec.shutdown();
    }

    public static void test(IntGenerator g) {
        test(g, 20);
    }
}
