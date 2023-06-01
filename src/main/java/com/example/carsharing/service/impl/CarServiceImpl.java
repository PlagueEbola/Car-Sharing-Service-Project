package com.example.carsharing.service.impl;

import com.example.carsharing.model.Car;
import com.example.carsharing.repository.CarRepository;
import com.example.carsharing.service.CarService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find car by id " + id));
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void update(Long id, Car car) {
        car.setId(id);
        carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public boolean carExist(Car car) {
        Optional<Car> carFromDbOptional =
                carRepository.findByBrandAndModel(
                car.getBrand(),
                car.getModel()
                );
        if (carFromDbOptional.isEmpty()) {
            return false;
        }
        Car carFromDB = carFromDbOptional.get();
        return carFromDB.getBrand().equals(car.getBrand())
                && carFromDB.getModel().equals(car.getModel());
    }
}
