package com.example.car_sharing_sertvice_project.service;

import com.example.car_sharing_sertvice_project.model.Car;
import java.util.List;

public interface CarService {
    Car find(Integer id);

    Car save(Car car);

    void delete(Integer id);

    List<Car> getAll();
}
