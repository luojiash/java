package com.luojiash.zookeeper;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 《从Paxos到Zookeeper》p154
 */
public class Recipes_CyclicBarrier {
    public static CyclicBarrier barrier = new CyclicBarrier(3);
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1 player")));
        executor.submit(new Thread(new Runner("2 player")));
        executor.submit(new Thread(new Runner("3 player")));
        executor.shutdown();
    }
}

class Runner implements Runnable {
    private String name;
    public Runner(String name){
        this.name = name;
    }
    public void run() {
        System.out.println(name+"ready.");
        try {
            Recipes_CyclicBarrier.barrier.await();
        } catch (Exception e) {e.printStackTrace();}
        System.out.println(name+"start");
    }
}