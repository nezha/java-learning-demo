package com.nezha.learn.demo.stream.peek;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: PeekSample
 *
 * @author: chentong
 * Date:     2019/1/19 22:28
 */
public class PeekSample {

    @Test
    public void test_printInfoButNot() {
        Person a = new Person("a", 18);
        Person b = new Person("b", 23);
        Person c = new Person("c", 34);
        Stream<Person> persons = Stream.of(a, b, c);
        persons.peek(System.out::println);
    }

    @Test
    public void test_printInfo() {
        Person a = new Person("a", 18);
        Person b = new Person("b", 23);
        Person c = new Person("c", 34);
        Stream<Person> persons = Stream.of(a, b, c);
        persons.forEach(System.out::println);
    }

    @Test
    public void test_peekDebug() {
        Person a = new Person("a", 18);
        Person b = new Person("b", 23);
        Person c = new Person("c", 34);
        Stream<Person> persons = Stream.of(a, b, c);
        persons.filter(person -> person.getAge() < 30)
                .peek(person -> System.out.println("filter " + person))
                .map(person -> new Person(person.getName() + " map", person.getAge()))
                .peek(person -> System.out.println("map " + person))
                .collect(Collectors.toList());
    }

    @Test
    public void test_modifyInnerState() {
        Person a = new Person("a", 18);
        Person b = new Person("b", 23);
        Person c = new Person("c", 34);
        Stream<Person> persons = Stream.of(a, b, c);
        persons.peek(person -> person.setName(person.getName().toUpperCase()))
                .forEach(System.out::println);
    }
}
