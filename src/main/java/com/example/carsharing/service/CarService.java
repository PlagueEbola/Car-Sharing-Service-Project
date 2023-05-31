package com.example.carsharing.service;

import com.example.carsharing.model.Car;
import java.util.List;

public interface CarService {
    Car getById(Integer id);

    Car save(Car car);

    void deleteById(Integer id);

    List<Car> getAll();
}
