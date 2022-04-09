package com.company;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(()-> main.task1())
                .thenApplyAsync(t1-> main.task2(t1))
                .thenAccept(t2t3 -> main.task3(t2t3))
                        .handle((tx, ex) -> {
                            if(ex != null){
                                ex.printStackTrace();
                            }
                            return "exception";
                        });
        System.out.println("Segundo commit");
        System.out.println(completableFuture.get());
    }

    String task1() {
        for(int i=0; i<10; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " task 1 - " + i);
        }
        System.out.println("Tercer commit");
        return "task1";
    }

    String task2(String t1) {
        for(int i=0; i<10; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " task 2- " + i);
        }
        return t1 + "task2";
    }

    String task3(String t1t2) {
        for(int i=0; i<10; i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " task 3- " + i);
        }
        return t1t2 + "task3";
    }
}
