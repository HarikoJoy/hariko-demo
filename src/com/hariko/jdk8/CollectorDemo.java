package com.hariko.jdk8;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorDemo {

    public static void main(String[] args) {
        Student student1 = new Student("zhangsan", 100, 20);
        Student student2 = new Student("lisi", 90, 20);
        Student student3 = new Student("wangwu", 90 , 30);
        Student student4 = new Student("zhaoliu", 80, 40);

        List<Student> list = Arrays.asList(student1, student2, student3, student4);

        //demo1(list);
        //demo2();
        demo3();
    }

    public static void demo3(){
        List<String> list = Arrays.asList("hello", "world", "welcome");

        list.stream().collect(new Hariko1Collector<>()).forEach((k, v) -> System.out.println("k = " + k + ", v = " + v));
    }

    public static void demo2(){
        List<String> list = Arrays.asList("hello", "world", "welcome");

        list.stream().collect(new HarikoCollector<>()).stream().forEach(System.out::println);
    }

    public static void demo1(List<Student> list){
        Map<String, List<Student>> stringListMap = list.stream().collect(Collectors.groupingBy(Student::getName));

        list.stream().collect(Collectors.counting());
        System.out.println(list.stream().count());

        //分数最小值
        list.stream().collect(Collectors.minBy(Comparator.comparingInt(Student::getScore))).ifPresent(System.out::println);
        list.stream().collect(Collectors.averagingInt(Student::getScore));
        IntSummaryStatistics intSummaryStatistics = list.stream().collect(Collectors.summarizingInt(Student::getScore));

        //按学生姓名分组,并返回每个组的分数最小的那个学生
        list.stream().collect(Collectors.groupingBy(Student::getName, Collectors.minBy(Comparator.comparingInt(Student::getScore))));
    }
}

class HarikoCollector<T> implements Collector<T, Set<T>, Set<T>>{

    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked !");
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked !");

        //return HashSet<T>::add;  //不可以这样写,因为与supplier冲突
        //return Set<T>::add;

        return (set, t) -> set.add(t);
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked !");

        return (set1, set2) -> {set1.addAll(set2);return set1;};
    }

    @Override
    public Function<Set<T>, Set<T>> finisher() {
        System.out.println("finisher invoked !");
        return set -> set;

        //return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked !");
        //IDENTITY_FINISH identity 和 finisher 是一样的函数
        //return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED));

        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }
}

class Hariko1Collector<T> implements Collector<T, Map<T, T>, Map<T, T>>{

    @Override
    public Supplier<Map<T, T>> supplier() {
        System.out.println("supplier invoked !");
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, T>, T> accumulator() {
        System.out.println("accumulator invoked !");

        //return HashSet<T>::add;  //不可以这样写,因为与supplier冲突
        //return Set<T>::add;

        return (map, t) -> map.put(t, t);
    }

    @Override
    public BinaryOperator<Map<T, T>> combiner() {
        System.out.println("combiner invoked !");

        return (map1, map2) -> {map1.putAll(map2);return map1;};
    }

    @Override
    public Function<Map<T, T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked !");
        return map -> map;

        //return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked !");
        //IDENTITY_FINISH identity 和 finisher 是一样的函数
        //return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED));

        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }
}

class Student{
    public Student(String name, int score, int age) {
        this.name = name;
        this.score = score;
        this.age = age;
    }

    private String name;
    private int score;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", age=" + age +
                '}';
    }
}