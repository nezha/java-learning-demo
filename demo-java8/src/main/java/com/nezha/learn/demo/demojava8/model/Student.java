package com.nezha.learn.demo.demojava8.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/3/28 10:10 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Data
@Builder
@AllArgsConstructor
public class Student {
    Long id;
    String name;
    String sex;
    Integer age;
}
