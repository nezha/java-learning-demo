package com.nezha.learn.demo.demojava8.main;

import com.alibaba.fastjson.JSON;
import com.nezha.learn.demo.demojava8.model.Student;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/3/28 10:13 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        StreamOps streamOps = new StreamOps();
        streamOps.useStreamOps();

    }
}
