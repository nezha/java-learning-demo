package com.nezha.learn.demo.stream.flatmap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: FlatmapExample
 *
 * @author: chentong
 * Date:     2019/7/26 0:45
 */
public class FlatmapExample {


    /**
     * convert List<List<>>  to List<>
     */
    @Test
    public void test_mergeList_success() {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(4,5,6));
        lists.add(list1);
        lists.add(list2);

        List<Integer> flatList = lists.stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(flatList);
    }
}
