package com.nezha.learn.demo.dto;

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
public class CarDto {
    private String make;
    private int seatCount;
    private String type;
    private String description;

    public void setType(CarType carType) {
        this.type = carType.getType();
    }
}
