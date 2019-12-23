package com.hariko.jdk8;

import java.util.Date;
import java.util.function.*;

public class FunctionDemo {
    public static void main(String[] args) {
        Supplier supplier = () -> {
            return 1;
        };
        System.out.println("supplier: " + supplier.get());

        Consumer consumer = (i) -> {
        };

        Function function = (r) -> {
            return r;
        };

        Predicate predicate = (i) -> {
            return Boolean.TRUE;
        };

        Date now = new Date();
        //方法引用
        Supplier supplierForDate = now::getTime;
        //静态方法引用
        Supplier supplierForSystem = System::currentTimeMillis;
        //类名引用实例方法
        Function<String, Integer> functionForString = String::length;
        System.out.println(functionForString.apply("Str"));

        BiFunction<String, Integer, String> biFunctionForString = String::substring;
        System.out.println(biFunctionForString.apply("Str", 0));

        //引用类的构造方法
        Supplier supplierForString = String::new;
        //引用数组的构造方法
        Function<Integer, Integer[]> functionForArray = Integer[]::new;

    }
}

