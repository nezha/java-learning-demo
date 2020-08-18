package com.nezha.learn.demo.stream.filter;

import com.google.common.collect.Collections2;
import io.vavr.collection.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: FilterCase
 *
 * @author: chentong
 * Date:     2019/1/9 8:47
 */
public class FilterCase {

    @Test
    public void test_findEvenNumber_returnSuccess() throws Exception {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> evenNumbers = findEvenNumbersUserStream(numbers);
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }

    private List<Integer> findEvenNumbersUserStream(List<Integer> baseCollection) {
        Predicate<Integer> streamsPredicate = item -> item % 2 == 0;
        return baseCollection.stream()
                .filter(streamsPredicate)
                .collect(Collectors.toList());
    }

    @Test
    public void test_findEvenNumbersUseEclipse_returnSuccess() throws Exception {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> evenNumbers = findEvenNumbersUseEclipse(numbers);
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }


    public List<Integer> findEvenNumbersUseEclipse(List<Integer> input) {
        org.eclipse.collections.api.block.predicate.Predicate<Integer> eclipsePredicate
                = item -> item % 2 == 0;

        List<Integer> filteredList = Lists.mutable
                .ofAll(input)
                .select(eclipsePredicate);
        return filteredList;
    }

    @Test
    public void test_findEvenNumbersUseEclipseWithStaticMethod_returnSuccess() throws Exception {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> evenNumbers = findEvenNumbersUseEclipseWithStaticMethod(numbers);
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }

    public List<Integer> findEvenNumbersUseEclipseWithStaticMethod(List<Integer> input) {
        org.eclipse.collections.api.block.predicate.Predicate<Integer> eclipsePredicate
                = item -> item % 2 == 0;
        return (List<Integer>) Iterate.select(input, eclipsePredicate);
    }

    @Test
    public void test_findEvenNumbersUseApache_returnSuccess() throws Exception {
        List<Integer> inputs = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> numbers = new LinkedList(inputs);
        Collection<Integer> evenNumbers = findEvenNumbersUseApache(numbers);
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }

    public Collection<Integer> findEvenNumbersUseApache(Collection<Integer> baseCollection) {
        org.apache.commons.collections4.Predicate<Integer> apachePredicate = item -> item % 2 == 0;

        CollectionUtils.filter(baseCollection, apachePredicate);
        return baseCollection;
    }

    @Test
    public void test_findEvenNumbersUseGuava_returnSuccess() throws Exception {
        List<Integer> inputs = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collection<Integer> evenNumbers = findEvenNumbersUseGuava(inputs);
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }

    public Collection<Integer> findEvenNumbersUseGuava(Collection<Integer> baseCollection) {
        com.google.common.base.Predicate<Integer> guavaPredicate = item -> item % 2 == 0;

        return Collections2.filter(baseCollection, guavaPredicate);
    }

    @Test
    public void test_findEvenNumbersUseVavr_returnSuccess() throws Exception {
        List<Integer> inputs = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> evenNumbers = Stream.concat(inputs).filter(integer -> integer.intValue() % 2 == 0).asJavaMutable();
        evenNumbers.forEach(integer -> assertTrue(integer % 2 == 0));
    }
}
