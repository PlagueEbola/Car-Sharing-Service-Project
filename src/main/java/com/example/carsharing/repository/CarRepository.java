package com.example.carsharing.repository;

import com.example.carsharing.model.Car;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByBrandAndModel(String brand, String model);
}
