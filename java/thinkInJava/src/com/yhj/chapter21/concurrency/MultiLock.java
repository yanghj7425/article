package com.yhj.chapter21.concurrency;

public class MultiLock {
    public synchronized void f1(int c){
        if(c>0){
            System.out.println("f1\t" + c--);
            f2(c);
        }
    }
    public synchronized void f2(int c){
        if (c>0){
            System.out.println("f2\t" + c--);
            f1(c);
        }
    }

    public static void main(String[] args) {
        MultiLock multiLock = new MultiLock();
        new Thread(){
            @Override
            public void run() {
                multiLock.f1(10);
                multiLock.f2(13);
            }
        }.start();
    }
}
