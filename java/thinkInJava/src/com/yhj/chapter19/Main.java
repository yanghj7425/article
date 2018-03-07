package com.yhj.chapter19;

public class Main {
    public static void main(String[] args) {
        for (Shrubbery s :
                Shrubbery.values()) {
            System.out.println(s + "\t" + s.getDescription());
            if (s.compareTo(Shrubbery.GROUND) == 0){
                System.out.println("compareTo");
            }
            if(s == Shrubbery.GROUND){
                System.out.println(" == ");
            }
        }
    }
}
