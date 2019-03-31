package com.nezha.learn.demo.demojava8.main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/3/31 10:47 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class StreamOps {

    public void useStreamOps(){
        //1.filter
        List<String> strings = Arrays.asList("Hollis", "", "HollisChuang", "H", "hollis");
        strings.stream().filter(string -> !string.isEmpty()).forEach(System.out::println);
        System.out.println("\n------------------------");

        //2.map
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers.stream().map( i -> i*i).forEach(System.out::println);
        System.out.println("\n------------------------");

        //3.limit/skip
        List<Integer> numbers2 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers2.stream().limit(4).forEach(System.out::println);
        System.out.println("\n------------------------");

        //4.sorted
        List<Integer> numbers3 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers3.stream().sorted().forEach(System.out::println);
        System.out.println("\n------------------------");

        //5.distinct
        List<Integer> numbers4 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers4.stream().distinct().forEach(System.out::println);
        System.out.println("\n------------------------");


        //6.demo
        List<String> stringList = Arrays.asList("Hollis", "HollisChuang", "hollis", "Hello", "HelloWorld", "Hollis");
        Stream s = stringList.stream().filter(string -> string.length()<= 6).map(String::length).sorted().limit(3).distinct();
        s.forEach(System.out::println);
        System.out.println("\n------------------------");


        //7.forEach
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        System.out.println("\n------------------------");

        //8.count
        List<String> strings2 = Arrays.asList("Hollis", "HollisChuang", "hollis","Hollis666", "Hello", "HelloWorld", "Hollis");
        System.out.println(strings2.stream().count());
        System.out.println("\n------------------------");

        //9.collect
        List<String> strings3 = Arrays.asList("Hollis", "HollisChuang", "hollis","Hollis666", "Hello", "HelloWorld", "Hollis");
        strings  = strings3.stream().filter(string -> string.startsWith("Hollis")).collect(Collectors.toList());
        System.out.println(strings);
        System.out.println("\n------------------------");
    }
}
