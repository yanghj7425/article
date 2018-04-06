package com.yhj.chapter21.concurrency;

import com.sun.xml.internal.ws.util.Pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable{

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting Sleeping run~");
    }
}
class IOBlocked implements Runnable{
    private InputStream in;
    public IOBlocked(InputStream input){
        this.in = input;
    }
    @Override
    public void run() {
        System.out.println("wait for read ...");
        try {
            in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting IOBlocked run ~");
    }
}

class  SynchronizedBlocked implements Runnable{
    public synchronized void  f(){
        while (true){
            Thread.yield();
        }
    }
   public SynchronizedBlocked(){
        new Thread(){
            public void run(){
                f();
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("try to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked run ~");
    }
}

public class Interrupting {
    private static ExecutorService service = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        Future<?> f = service.submit(r);
        TimeUnit.MICROSECONDS.sleep(100);
        System.out.print("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " +  r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("system.exit(0)");
        System.exit(0);
    }
}
