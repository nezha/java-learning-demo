package com.nezha.learn.demo.model;

import com.nezha.learn.demo.constant.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/4/3 10:40 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;

    public void setType(String type) {
        this.type = CarType.convert(type);
    }
}
