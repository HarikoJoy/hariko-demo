package com.hariko.juc.test;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 注意事项:
 * 1.size方法是要遍历一边集合的,用isEmpty去判断是否为空
 */
public class ConcurrentLinkedQueueTest {

    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {

    }
}
