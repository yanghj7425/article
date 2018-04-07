package com.yhj.chapter21.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "orderNum\t" + this.orderNum;
    }
}
class WaitPersion implements Runnable{

    Restaurant restaurant;

    public WaitPersion(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (! Thread.currentThread().isInterrupted()){
            try{
                synchronized (this){
                    while (restaurant.meal == null){
                        wait();
                    }
                }
                System.out.println("Waitpersion got \t" + restaurant.meal);
                synchronized (restaurant.chef){
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Chef implements Runnable{
    Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (! Thread.currentThread().isInterrupted()){
                synchronized (this){
                    while (restaurant.meal != null){
                        wait();
                    }
                }
                if (++ count == 10){
                    restaurant.service.shutdownNow();
                    return;
                }
                System.out.println("order up");
                synchronized (restaurant.waitPersion){
                    restaurant.meal = new Meal(count);
                    restaurant.waitPersion.notifyAll();
                }
                TimeUnit.MICROSECONDS.sleep(100);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

public class Restaurant {

    Meal meal;
    WaitPersion waitPersion = new WaitPersion(this);
    Chef chef = new Chef(this);
    ExecutorService service = Executors.newCachedThreadPool();

    Restaurant(){
        service.execute(chef);
        service.execute(waitPersion);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
