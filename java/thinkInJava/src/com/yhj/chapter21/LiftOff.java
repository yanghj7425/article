package com.yhj.chapter21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++; // 可用于区分任务的多个实例

    public LiftOff() {

    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "Over") + ")";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield(); // 暂时让出资源， vi. 退让、屈服、让位
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();  // 防止新任务被提交给这个 Executor ，当前线程将继续运行在 shutdown() 被调用之前提交的所有任务。
    }
}
