package com.example.car_sharing_sertvice_project.repository;

import com.example.car_sharing_sertvice_project.model.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Object> findAllByUserIdAndActualReturnDateIsNull(Integer userId);
    List<Object> findAllByUserIdAndActualReturnDateIsNotNull(Integer userId);
}
