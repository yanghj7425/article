package com.yhj.chapter19;

import java.util.EnumSet;

public class EnumSetMain {
    public static void main(String[] args) {
        EnumSet set = EnumSet.allOf(Shrubbery.class);
        System.out.println(set);
    }
}
