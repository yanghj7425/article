package com.yhj.chapter15.GenerType;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<? super Car> list = new ArrayList<Car>();
        list.add(new BicycleCar());
        list.add(new SingleCar());
        for(Object o : list){
            Car c = (Car) o;
            c.introduce();
        }
    }
}
