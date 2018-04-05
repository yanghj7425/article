package com.yhj.chapter14.ReflectClass;

public class WithPrivateFinalField {
    private int i = 0;
    private final String s;

    {
        i = 100;
        s = "this is final field";
    }
}
