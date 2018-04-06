package com.yhj.chapter21.concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionThread implements Runnable {
    @Override
    public void run() {

    }
}
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t);
    }
}
class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }
}


public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool(new HandlerThreadFactory());
        service.execute(new ExceptionThread());
    }
}
