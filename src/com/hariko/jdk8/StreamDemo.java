package com.hariko.jdk8;

import java.awt.desktop.SystemSleepEvent;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 1.流由三部分组成: a、源  b、零个或者多个中间操作  c、终止操作
 * 2.流操作的分类: a、惰性求值  b、及早求值
 * 3.collect、reduce、foreach 都是终止操作
 * 4.同一个stream只能使用一次
 */
public class StreamDemo {

    public static void main(String[] args) {
        //demo1();
        //demo2();
        //demo3();
        //demo4();
        //demo5();
        //demo6();
        //demo7();
        //demo8();
        //demo9();
        //demo10();
        //demo11();
        //demo12();
        //demo13();
        //demo14();
        demo15();
    }

    public static void demo15(){
        List<String> list1 = Arrays.asList("Hi", "Hello", "你好");
        List<String> list2 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        List<String> list3 = list1.stream().flatMap(i -> list2.stream().map(j -> i + " " + j)).collect(Collectors.toList());
        list3.stream().forEach(System.out::println);
    }

    //流的短路 流的去重
    public static void demo14(){
        List<String> list = Arrays.asList("hello", "hello", "world", "hello world");
        list.stream().mapToInt(i -> i.length()).filter(i -> i == 5).findFirst().ifPresent(System.out::println);

        //只打印出hello,因为流是先初始化完成,最后分批对每一个元素操作
        list.stream().mapToInt(i -> {
            System.out.println(i);
            return i.length();
        }).filter(i -> i == 5).findFirst().ifPresent(System.out::println);

        System.out.println("---------------------------");
        list.stream().map(i -> i.split(" ")).flatMap(Arrays::stream).distinct().forEach(System.out::println);
    }

    //并行流
    public static void demo13(){
        /*List<String> list1 = Stream.iterate("1", i -> UUID.randomUUID().toString()).limit(5000000).collect(Collectors.toList());

        System.out.println("开始并行排序: ");
        long startTime1 = System.nanoTime();
        System.out.println("总数为: " + list1.parallelStream().sorted().count());
        long endTime1 = System.nanoTime();
        long millis1 = TimeUnit.NANOSECONDS.toMillis(endTime1 - startTime1);
        System.out.println("并行耗时为: " + millis1);*/

        List<String> list = Stream.iterate("1", i -> UUID.randomUUID().toString()).limit(5000000).collect(Collectors.toList());
        System.out.println("开始串行排序: ");
        long startTime = System.nanoTime();
        System.out.println("总数为: " + list.stream().sorted().count());
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("串行耗时为: " + millis);
    }

    public static void demo12(){
        List<String> list = Arrays.asList("hello", "world", "helloworld");
        list.stream().map(i -> i.substring(0, 1).toUpperCase() + i.substring(1)).forEach(System.out::println);
        System.out.println("----------------------------");

        list.stream().map(item -> {
            String temp = item.substring(0, 1).toUpperCase() + item.substring(1);
            System.out.println("test");
            return temp;
        }).forEach(System.out::println);
    }

    public static void demo11(){
        Stream<Integer> stream = Stream.iterate(1, i -> i + 2).limit(6);
        System.out.println(stream);
        Stream<Integer> stream1 = stream.filter(i -> i > 2);
        System.out.println(stream1);
        Stream<Integer> stream2 = stream1.distinct();
        System.out.println(stream2);
    }

    //找出大于2的元素,然后将每个元素乘以2,然后跳过前两个元素,在取前两个元素,最后求出总和
    public static void demo10(){
        long count = Stream.iterate(1, i -> i + 2).limit(6).filter( i -> i > 2)
                .mapToLong(i -> i * 2).skip(2).limit(2).sum();

        System.out.println(count);
        System.out.println("---------------------------");
        IntSummaryStatistics intSummaryStatistics = Stream.iterate(1, i -> i + 2).limit(6).filter( i -> i > 2)
                .mapToInt(i -> i * 2).skip(2).limit(2).summaryStatistics();

        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getAverage());

    }

    //generate optional 的应用
    public static void demo9(){
        Stream<String> stream = Stream.generate(UUID.randomUUID()::toString);
        stream.findFirst().ifPresent(System.out::println);
    }

    //flatMap 平滑式的map
    public static void demo8(){
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        stream.flatMap(list -> list.stream()).map(item -> item * item).forEach(System.out::println);
    }

    //集合中所有的元素全部转换为大写然后输出
    public static void demo7(){
        Stream<String> stream = Stream.of("hello", "world", "helloworld");
        stream.map(String::toUpperCase).forEach(System.out::println);
    }

    public static void demo6(){
        Stream<String> stream = Stream.of("hello", "world", "helloworld");
        //List<String> list = stream.collect(Collectors.toList());
        //List<String> list = stream.collect(ArrayList::new, List::add, (list1, list2) -> list1.addAll(list2));
        List<String> list = stream.collect(() -> new ArrayList<String>(), (list1, item) -> list1.add(item),
                (list1, list2) -> list1.addAll(list2));
        list.stream().forEach(System.out::println);
    }

    public static void demo5() {
        List<String> list = new ArrayList<>();
        list.add("ss");
        list.stream().toArray(i -> {
            System.out.println(i);
            return new String[i];
        });
    }

    public static void demo4() {
        Stream<String> stream = Stream.of("hello", "world", "helloworld");
        /*String[] strArray = stream.toArray(i -> {
            return new String[i];
        });*/
        String[] strArray = stream.toArray(String[]::new);
        Arrays.stream(strArray).forEach(System.out::println);
    }

    public static void demo3() {
        //从1到6的数值,每个数乘以2,并累加它们的和
        int count = IntStream.rangeClosed(1, 6).map(i -> {
            return i * 2;
        }).reduce(Integer::sum).getAsInt();
        System.out.println(count);
        System.out.println("------------------------");
    }

    public static void demo2() {
        IntStream.of(new int[]{5, 6, 7}).forEach(System.out::println);
        System.out.println("-------------");

        IntStream.range(3, 8).forEach(System.out::println);
        System.out.println("-----------------");

        IntStream.rangeClosed(3, 8).forEach(System.out::println);
    }


    public static void demo1() {
        Stream<String> stream = Stream.of("A", "B", "C", "D");
        stream.map(i -> i.toLowerCase()).forEach(str -> System.out.print(str));
        System.out.println();
        stream = Stream.of("A", "B", "C", "D");
        stream.filter(i -> i.equals("A")).forEach(str -> System.out.print(str));

        String[] array = {"a", "b", "c"};
        Stream<String> stream1 = Stream.of(array);

        Stream<String> stream2 = Arrays.stream(array);
    }
}
