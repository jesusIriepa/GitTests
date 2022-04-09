package com.company;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("Añadido en el tercer commit");
    Main main = new Main();
    CompletableFuture<DataDto> completableFuture =
        CompletableFuture.supplyAsync(() -> main.task1())
            .thenApplyAsync(t1 -> main.task2(t1))
            .thenApplyAsync(t1t2 -> main.task4(t1t2))
            .thenApplyAsync(t2t3 -> main.task3(t2t3))
            .thenApplyAsync(ret -> main.task5(ret))
            .handle(
                (tx, ex) -> {
                  if (ex != null) {
                    ex.printStackTrace();
                  }
                  return tx;
                });
    System.out.println("Segundo commit");
    System.out.println(completableFuture.get());
  }

  String task1() {
    for (int i = 0; i < 10; i++) {
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
    for (int i = 0; i < 10; i++) {
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
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getId() + " task 3- " + i);
    }
    return t1t2 + "task3";
  }

  String task4(String text) {
    System.out.println("received: " + text);
    return text + " taks4 modified";
  }

  DataDto task5(String text) {
    DataDto dataDto = new DataDto();
    dataDto.text = text;
    dataDto.number = new Random().nextInt();
    return dataDto;
  }
}
