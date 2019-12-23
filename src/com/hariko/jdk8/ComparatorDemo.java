package com.hariko.jdk8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {

    static {
        i = 1;
    }
    static int i;

    public static void main(String[] args) {
        List<String> list = Arrays.asList("nihao", "hello", "world", "welcome");

        demo1(list);
    }

    public static void demo1(List<String> list){
        Collections.sort(list, (item1, item2) -> item1.length() - item2.length());
        System.out.println(list);
        System.out.println("------------------");

        Collections.sort(list, (item1, item2) -> -(item1.length() - item2.length()));
        System.out.println(list);
        System.out.println("--------------------------");

        //按照字符串的长度的升序排列
        Collections.sort(list, Comparator.comparingInt(String::length));
        System.out.println("----------------------------");
        Collections.sort(list, Comparator.comparingInt(String::length).reversed());
        System.out.println("-------------------------");

        //自动会编译成object,要显式的指定
        Collections.sort(list, Comparator.comparingInt((String i) -> i.length()).reversed());
        System.out.println("-----------------------------");
        Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));

    }
}
