package com.example.carsharing.controller;

import com.example.carsharing.dto.request.CarRequestDto;
import com.example.carsharing.dto.response.CarResponseDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.mapper.CarMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private final CarService service;
    private final CarMapper mapper;

    @PostMapping
    @Operation(summary = "Adds a car to local db")
    public CarResponseDto save(@RequestBody CarRequestDto dto) {
        return mapper.toResponseDto(service.save(mapper.toModel(dto)));
    }

    @GetMapping
    @Operation(summary = "Get all cars from local db")
    public List<Car> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a car from local db")
    public CarResponseDto getById(@PathVariable Long id) {
        return mapper.toResponseDto(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a car in db")
    public void update(@PathVariable Long id,
                       @RequestBody
                       CarRequestDto dto) {
        Car car = mapper.toModel(dto);
        car.setId(id);
        service.save(car);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a car from local")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
