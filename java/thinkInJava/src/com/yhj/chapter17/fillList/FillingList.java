package com.yhj.chapter17.fillList;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StringAddress {
    private String s;

    StringAddress(String str) {
        this.s = str;
    }

    @Override
    public String toString() {
        return "[+\t" + this.s + "\t+]";
    }
}

public class FillingList {
    public static void main(String[] args) {
        List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(2, new StringAddress("hello")));
        System.out.println(list);
    }
}
