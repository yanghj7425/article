package com.yhj.chapter21.sharelimit;

public class EventGenertor extends IntGenerator {
    private int currentEventValue = 0;

    @Override
    public  int next() {
        ++currentEventValue;
        Thread.yield();
        ++currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new EventGenertor());
    }
}
