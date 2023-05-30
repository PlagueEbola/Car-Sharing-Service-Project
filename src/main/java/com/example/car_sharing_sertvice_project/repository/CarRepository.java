package com.example.car_sharing_sertvice_project.repository;

import com.example.car_sharing_sertvice_project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
