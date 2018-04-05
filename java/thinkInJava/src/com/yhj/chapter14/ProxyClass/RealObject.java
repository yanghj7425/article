package com.yhj.chapter14.ProxyClass;

public class RealObject implements Interface {
    @Override
    public void doSomeThing() {
        System.out.println("doSomething");
    }

    @Override
    public void doSomeThingElse(String arg) {
        System.out.println("doSomeThingElse\t" + arg);
    }
}
