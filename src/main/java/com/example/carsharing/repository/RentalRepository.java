package com.example.carsharing.repository;

import com.example.carsharing.model.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findAllByUserIdAndActualReturnDateIsNull(Integer userId);

    List<Rental> findAllByUserIdAndActualReturnDateIsNotNull(Integer userId);
}
