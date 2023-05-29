package com.example.car_sharing_sertvice_project.service.impl;

import com.example.car_sharing_sertvice_project.model.Car;
import com.example.car_sharing_sertvice_project.repository.CarRepository;
import com.example.car_sharing_sertvice_project.service.CarService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car find(Integer id) {
        return carRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find car by id " + id));
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
}
