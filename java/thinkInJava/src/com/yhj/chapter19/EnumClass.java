package com.yhj.chapter19;

public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()){
            System.out.println(s);
            System.out.println(s.getClass());
        }
    }

}
