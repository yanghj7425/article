package com.yhj.chapter19;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapMain {
    public static void main(String[] args) {
        EnumMap<Shrubbery, Command> map = new EnumMap<Shrubbery, Command>(Shrubbery.class);
        map.put(Shrubbery.GROUND, new Command() {
            @Override
            public void action() {
                System.out.println("GROUND Commond");
            }
        });
        for (Map.Entry<Shrubbery, Command> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().action();
        }
    }
}
