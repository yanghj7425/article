package com.yhj.chapter14.ReflectClass;

import java.lang.reflect.Field;

public class PrivateFIeldTest {

    public static void main(String[] args) throws Exception {
        WithPrivateFinalField pf = new WithPrivateFinalField();
        Field f = pf.getClass().getDeclaredField("i");
        f.setAccessible(true);
        out(f, pf);
        f.set(pf, 99);
        System.out.println("***********************changed ***************");
        out(f, pf);

        System.out.println("*****************obtain final field *************");
        f = pf.getClass().getDeclaredField("s");
        f.setAccessible(true);
        out(f, pf);
    }

    private static void out(Field f, Object pf) throws IllegalAccessException {
        System.out.println("private field int \t" + f.getName() + "\t" + f.getType() + "\t" + f.get(pf));
    }
}
