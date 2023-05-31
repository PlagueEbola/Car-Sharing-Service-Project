package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.RentalRequestDto;
import com.example.carsharing.dto.response.RentalResponseDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.Rental;
import com.example.carsharing.model.User;

public class RentalMapper {
    public Rental toModel(RentalRequestDto requestDto) {
        Rental rental = new Rental();
        rental.setRentalDate(requestDto.getRentalDate());
        User user = new User();
        user.setId(requestDto.getUserId());
        rental.setUser(user);
        Car car = new Car();
        car.setId(requestDto.getCarId());
        rental.setCar(car);
        rental.setReturnDate(requestDto.getReturnDate());
        rental.setActualReturnDate(requestDto.getActualReturnDate());
        return rental;
    }

    public RentalResponseDto toResponseDto(Rental rental) {
        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setId(responseDto.getId());
        responseDto.setRentalDate(rental.getRentalDate());
        responseDto.setReturnDate(rental.getReturnDate());
        responseDto.setActualReturnDate(rental.getActualReturnDate());
        responseDto.setUserId(rental.getUser().getId());
        responseDto.setCarId(rental.getCar().getId());
        return responseDto;
    }
}
