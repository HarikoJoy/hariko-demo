package com.hariko.juc.test;

import java.util.concurrent.CompletableFuture;

public class CompleteFutureTest {

    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> {

        }).whenComplete((v, t) -> System.out.println("down"));
    }
}
