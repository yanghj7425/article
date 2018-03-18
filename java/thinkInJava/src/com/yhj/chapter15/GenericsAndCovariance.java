package com.yhj.chapter15;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericsAndCovariance {
    public static void main(String[] args) {
        List<Fruit> list = new ArrayList<Fruit>();
        writeTo(list, new Apple());
        writeTo(list, new Fruit());
        Iterator<Fruit> itr = list.iterator();
        while (itr.hasNext()) {
            Fruit fruit = itr.next();
            fruit.type();
        }
    }

    static <T> void writeTo(List<? super T> list, T item) {
        list.add(item);
    }
}
