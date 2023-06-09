package com.example.carsharing.controller;

import com.example.carsharing.dto.request.RentalRequestDto;
import com.example.carsharing.dto.response.RentalResponseDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.Rental;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.RentalService;
import com.example.carsharing.service.mapper.RentalMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
@AllArgsConstructor
public class RentalController {
    private final RentalService service;
    private final CarService carService;
    private final RentalMapper mapper;

    @PostMapping
    @Operation(summary = "initiate rent")
    public RentalResponseDto save(@RequestBody @Valid RentalRequestDto dto) {
        Rental rental = mapper.toModel(dto);
        Car car = carService.getById(rental.getCar().getId());
        if (car.getInventory() < 1) {
            throw new RuntimeException("This car is already in rent");
        }
        car.setInventory(car.getInventory() - 1);
        carService.save(car);
        return mapper.toResponseDto(service.save(rental));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get rental by id")
    public RentalResponseDto getById(@PathVariable Long id) {
        return mapper.toResponseDto(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "get all user's rentals")
    public List<RentalResponseDto> getByUser(@RequestParam(value = "user_id") Long userId,
                                             @RequestParam(value = "is_active") Boolean isActive) {
        return service.getByUserIdAndStatus(userId, isActive)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "return rental, car count will be increased and actual return time set")
    public RentalResponseDto returnRental(@PathVariable Long id) {
        Rental rental = service.getById(id);
        Car car = rental.getCar();
        car.setInventory(car.getInventory() + 1);
        carService.save(car);
        rental.setActualReturnDate(LocalDate.now());
        return mapper.toResponseDto(service.save(rental));
    }
}
