package com.yhj.chapter21.concurrency;

import java.util.concurrent.TimeUnit;

class YeildMask {
    public synchronized void f() throws InterruptedException {
        System.out.println("f()");
        notifyAll();
        Thread.yield(); // 和 sleep 一样不会释放资源锁
        double d = 0.0;
        for (int i = 0; i < 10010; i++) {
            d = Math.PI * Math.E + d;
        }

        System.out.println("f()  exit ");
    }

    public synchronized void f2() throws InterruptedException {

        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("f2()");
                wait();
                System.out.println("f2() wait exit");
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public synchronized void callNotify() {
        System.out.println("notify execute");
        notifyAll(); // 只能在同步块中调用
    }

    public synchronized boolean interrupted() {
        return Thread.currentThread().isInterrupted();
    }
}

public class YieldLock {
    public static void main(String[] args) throws InterruptedException {
        YeildMask mask = new YeildMask();
        waitTest(mask);
    }

    public static void waitTest(YeildMask mask) throws InterruptedException {
        new Thread() {
            public void run() {
                try {
                    mask.f2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println(mask.interrupted());
        mask.callNotify();
        mask.f();
    }

    public static void yeildTest(YeildMask mask) throws InterruptedException {
        new Thread() {
            public void run() {
                try {
                    mask.f();
                    mask.callNotify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        mask.f2(); // 不会返回。
        //  mask.callNotify(); // 此处无法唤醒 f2()，中调用了 wait 方法不会返回。
    }
}

