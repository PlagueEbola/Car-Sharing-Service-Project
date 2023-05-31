package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.CarRequestDto;
import com.example.carsharing.dto.response.CarResponseDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.CarType;

public class CarMapper {
    public Car toModel(CarRequestDto requestDto) {
        Car car = new Car();
        car.setModel(requestDto.getModel());
        car.setBrand(requestDto.getBrand());
        car.setInventory(requestDto.getInventory());
        car.setDailyFee(requestDto.getDailyFee());
        CarType type = new CarType();
        type.setTypeName(CarType.TypeName.valueOf(requestDto.getType()));
        car.setType(type);
        return car;
    }

    public CarResponseDto toResponseDto(Car car) {
        CarResponseDto responseDto = new CarResponseDto();
        responseDto.setId(car.getId());
        responseDto.setType(car.getType().getTypeName().name());
        responseDto.setModel(car.getModel());
        responseDto.setInventory(car.getInventory());
        responseDto.setBrand(car.getBrand());
        responseDto.setDailyFee(car.getDailyFee());
        return responseDto;
    }
}
