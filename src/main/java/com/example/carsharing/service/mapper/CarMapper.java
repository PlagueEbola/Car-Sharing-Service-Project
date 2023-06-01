package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.CarRequestDto;
import com.example.carsharing.dto.response.CarResponseDto;
import com.example.carsharing.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public Car toModel(CarRequestDto requestDto) {
        Car car = new Car();
        car.setModel(requestDto.getModel());
        car.setBrand(requestDto.getBrand());
        car.setInventory(requestDto.getInventory());
        car.setType(Car.CarType.valueOf(requestDto.getType()));
        car.setDailyFee(requestDto.getDailyFee());
        return car;
    }

    public CarResponseDto toResponseDto(Car car) {
        CarResponseDto responseDto = new CarResponseDto();
        responseDto.setId(car.getId());
        responseDto.setType(car.getType().name());
        responseDto.setDailyFee(car.getDailyFee());
        responseDto.setModel(car.getModel());
        responseDto.setInventory(car.getInventory());
        responseDto.setBrand(car.getBrand());
        return responseDto;
    }
}
