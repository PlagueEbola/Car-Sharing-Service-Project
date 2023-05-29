package com.example.car_sharing_sertvice_project.service;

import com.example.car_sharing_sertvice_project.model.Car;
import java.util.List;

public interface CarService {
    Car getById(Integer id);

    Car save(Car car);

    void deleteById(Integer id);

    List<Car> getAll();
}
