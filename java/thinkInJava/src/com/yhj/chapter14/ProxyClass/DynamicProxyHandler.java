package com.yhj.chapter14.ProxyClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy:\t" + proxy.getClass());
        System.out.println("method:\t" + method);
        System.out.println("args:\t" + args);
        System.out.println("proxied:\t" + proxied);
        return method.invoke(proxied, args);
    }
}
