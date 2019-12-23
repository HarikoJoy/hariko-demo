package com.hariko.jdk10;

import java.util.List;
import java.util.Set;

public class ReadOnlyColletionDemo {
    public static void main(String[] args) {
        List list = List.of("zhangsan", "lisi", "wangwu");
        System.out.println(list);
        Set set = Set.of("zhangsan", "lisi", "wangwu");
        System.out.println(set);
    }
}
