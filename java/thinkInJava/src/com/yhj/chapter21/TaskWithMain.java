package com.yhj.chapter21;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskWithMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < 5; i++) {
            futures.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
        Thread t;
        t = new Thread(new Runnable() {

            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {

            }
        });
        t.setDaemon(true);
    }
}
