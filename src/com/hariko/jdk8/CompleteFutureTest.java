package com.hariko.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 1. 所有的价格查证是同步方式提供的
 * 2. shop在返回价格的同时会返回一个折扣码
 * 3. 我们需要解析返回的字符串,并且根据折扣码获取折扣后的价格
 * 4. 折扣后的价格计算依然是同步进行的
 * 5. 查询价格返回的字符串格式为 shopName:price:discountCode("沃尔马:200:15")
 */
public class CompleteFutureTest {

    public static void main(String[] args) throws Exception{
        List<Shop> shopList = Arrays.asList(new Shop("A"), new Shop("B"), new Shop("C"),
                new Shop("D"), new Shop("E"), new Shop("F"), new Shop("G"), new Shop("H"),
                new Shop("I"), new Shop("J"));
        String product = "iPhoneX";

        //demo1();

        demo2(shopList, product);
    }

    public static void demo3(List<Shop> shopList, String product){
        Executor myExecutor = Executors.newFixedThreadPool(Math.min(shopList.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<String> list = shopList.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceFormat(product), myExecutor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), myExecutor)))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    //同步执行
    public static void demo2(List<Shop> shopList, String product){
        List<String> list = shopList.stream().map(shop -> shop.getPriceFormat(product))
                .map(Quote::parse).map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public static void demo1() throws Exception{
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在执行......");
                completableFuture.complete("success");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println(Thread.currentThread().getName() + "获取结果为: " + completableFuture.get());
    }
}

class Quote{
    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String str){
        String[] split = str.split(":");
        String shopName = split[0];
        Double price = Double.valueOf(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);

        return new Quote(shopName, price, code);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}

class Discount{
    public enum Code{
        NONE(0), SLIVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percantage;

        Code(int percantage){
            this.percantage = percantage;
        }
    }

    public static String applyDiscount(Quote quote){

        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    public static Double apply(double price, Discount.Code code){
        delay();
        return (price * (100 - code.percantage)) / 100;
    }

    private static Random random = new Random();

    private static void delay(){
        try {
            int delay = 500 + random.nextInt(2000);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Shop{
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPriceFormat(String product){
        double price = calculatePrice(product);

        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];

        return String.format("%s:%.2f:%s", name, price, code);
    }

    private static double calculatePrice(String product){
        delay();

        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private static Random random = new Random();

    private static void delay(){
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
