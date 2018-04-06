package com.yhj.chapter21.concurrency;

class DualObject {
    private Object syncObject = new Object();

    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
        }
    }

    public void g() {
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }
}

public class SyncObject {
    public static void main(String[] args) {
        DualObject dualObject = new DualObject();
        new Thread(){
            public void run(){
                dualObject.f();
            }
        }.start();
        dualObject.g();
    }

}
