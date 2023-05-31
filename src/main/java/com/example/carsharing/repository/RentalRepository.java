package com.example.carsharing.repository;

import com.example.carsharing.model.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByUserIdAndActualReturnDateIsNull(Long userId);

    List<Rental> findAllByUserIdAndActualReturnDateIsNotNull(Long userId);
}
