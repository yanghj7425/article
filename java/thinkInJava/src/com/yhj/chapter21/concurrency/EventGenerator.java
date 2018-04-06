package com.yhj.chapter21.concurrency;

public class EventGenerator extends IntGenerator {
    private int currentEvent = 0;
    @Override
    public int nextInt() {
        ++ currentEvent;
        ++ currentEvent;
        return currentEvent;
    }

    public static void main(String[] args) {
        EventChecker.test(new EventGenerator());
    }
}
