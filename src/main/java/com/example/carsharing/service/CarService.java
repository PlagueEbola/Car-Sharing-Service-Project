package com.example.carsharing.service;

import com.example.carsharing.model.Car;
import java.util.List;

public interface CarService {
    Car getById(Long id);

    Car save(Car car);

    void deleteById(Long id);

    List<Car> getAll();
}
