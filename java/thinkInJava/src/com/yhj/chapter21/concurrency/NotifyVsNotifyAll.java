package com.yhj.chapter21.concurrency;

import javafx.concurrent.Task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Blocker {
    synchronized void callWait() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                wait();
                System.out.print(Thread.currentThread() + "\t");
            }
        } catch (InterruptedException e) {

        }
    }

    synchronized void callNotify() {
        notify();
    }

    synchronized void callNotifyAll() {
        notifyAll();
    }
}

class Task1 implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.callWait();
        System.out.println(blocker + "\t" + this.getClass().getSimpleName());
    }
}

class Task2 implements Runnable {

   static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.callWait();
        System.out.println(blocker + "\t" + this.getClass().getSimpleName());
    }
}

public class NotifyVsNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.execute(new Task1());
        }
        service.execute(new Task2());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean switcher = true;
            @Override
            public void run() {
                if(switcher){
                    System.out.print("\r\nnotify\t");
                    Task1.blocker.callNotify();
                    switcher = false;
                }else {
                    System.out.print("\r\nnotityAll\t");
                    Task1.blocker.callNotifyAll();
                    switcher = true;
                }
            }
        },400,400);

        TimeUnit.SECONDS.sleep(3);
        timer.cancel();
        TimeUnit.MICROSECONDS.sleep(200);
        System.out.println("Task2.blocker.callNotifyAll \t");
        Task2.blocker.callNotifyAll();
        TimeUnit.MICROSECONDS.sleep(200);
        System.out.println("showdown");
        service.shutdownNow();
    }
}
