package com.yhj.chapter21.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    EventChecker(IntGenerator g, int d){
        this.generator = g;
        this.id = d;
    }

    @Override
    public void run() {
        while (! generator.isCanceled()){
            int val  = generator.nextInt();  // 同一时刻可能有多个线程执行到此处
            if( val % 2 != 0){
                System.out.println(val + "  not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator g, int count){
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            service.execute(new EventChecker(g,10));
        }
        service.shutdown();
    }

    public static void test(IntGenerator g){
        test(g, 10);
    }

}
