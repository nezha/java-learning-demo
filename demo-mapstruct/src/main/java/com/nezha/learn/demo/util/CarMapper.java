package com.nezha.learn.demo.util;

import com.nezha.learn.demo.dto.CarDto;
import com.nezha.learn.demo.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

/**
 * @Description: xxxxx <br>
 * @Date: 2019/4/3 10:41 PM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = ALWAYS,
        nullValueMappingStrategy = RETURN_DEFAULT)
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings({
            @Mapping(source = "numberOfSeats", target = "seatCount"),
            @Mapping(target = "description", ignore = true)
    })
    CarDto carToCarDto(Car car);

    Car carDtoToCar(CarDto carDto);
}
