package com.nezha.learn.demo;

import com.alibaba.fastjson.JSON;
import com.nezha.learn.demo.constant.CarType;
import com.nezha.learn.demo.dto.CarDto;
import com.nezha.learn.demo.model.Car;
import com.nezha.learn.demo.util.CarMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MapstructApplicationTests {

    @Autowired
    private CarMapper carMapper;

    @Test
    public void mapCarToDto() {
        //given
        Car car = new Car("Morris", 5, CarType.SAIC);

        //when
        CarDto carDto = carMapper.carToCarDto(car);

        //then
        Assert.assertNotNull(carDto);
        Assert.assertEquals("Morris", carDto.getMake());
        Assert.assertEquals("上汽", carDto.getType());
        log.info("输出的CarDto是：{}", JSON.toJSONString(carDto));
    }
    @Test
    public void carDtoToBean(){
        CarDto carDto = new CarDto("Morris",10,"奥迪","真的很不错");

        Car car = carMapper.carDtoToCar(carDto);
        log.info("输出的Car是：{}",JSON.toJSONString(car));
    }

}
