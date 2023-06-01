package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
  public Optional<Car> findByBrandAndModel(String brand, String model);
}
