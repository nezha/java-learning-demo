package com.nezha.learn.demo.demojava8.main;

import com.alibaba.fastjson.JSON;
import com.nezha.learn.demo.demojava8.model.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/3/31 10:45 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Slf4j
public class StudentsOps {

    public void useStudentStream(){
        List<Student> students = new ArrayList<>();
        students.add(new Student(111L, "aaa", "男", 1));
        students.add(new Student(222L, "bbb", "女", 3));
        students.add(new Student(333L, "ccc", "男", 2));
        students.add(new Student(444L, "ddd", "女", 4));
        students.add(null);
        log.info("\n>>>>初始化的student是：{}", students);
        //1.Optional防空利器
        Optional.ofNullable(students).orElse(Collections.emptyList()).forEach(System.out::println);

        log.info("\n>>>>>>>filter>>>>>>");
        //2.filter
        students.stream().filter(Objects::nonNull).forEach(student -> {
            System.out.println(student.getName());
        });

        log.info("\n>>>>>>>map>>>>>>");
        //3.map
        List<Long> studentIdList = students.stream().filter(Objects::nonNull).map(Student::getId).collect(Collectors.toList());
        studentIdList.forEach(System.out::println);

        log.info("\n>>>>>>list转换map>>>>>.");
        //4.list转换map
        Map<Long, Student> studentMap = students.stream().filter(Objects::nonNull).collect(Collectors.toMap(Student::getId, s -> s));
        log.info(JSON.toJSONString(studentMap));

        log.info("\n>>>>>>limit的使用>>>>>.");
        //5.limit的使用
        List<String> names = students.stream().limit(3).map(Student::getName).collect(Collectors.toList());
        names.forEach(System.out::println);

        log.info("\n>>>>>>skip的使用,下面是取第2条数据后面2条，可以实现分页功能啦>>>>>.");
        //6.skip 的使用
        List<String> names2 = students.stream().skip(2).limit(2).map(Student::getName).collect(Collectors.toList());
        names2.forEach(System.out::println);

        log.info("\n>>>>>>集合排序,默认是递增>>>>>.");
        //7.集合排序
        List<Student> studentsSorted = students.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Collections.sort(studentsSorted, Comparator.comparing(Student::getAge).reversed());
        studentsSorted.forEach(System.out::println);

        log.info("\n>>>>>>parallelStream，并行流>>>>>.");
        //8.parallelStream
        students.parallelStream().filter(Objects::nonNull).forEach(System.out::println);
    }
}
