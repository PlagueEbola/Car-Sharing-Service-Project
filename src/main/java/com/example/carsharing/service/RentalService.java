package com.example.carsharing.service;

import com.example.carsharing.model.Rental;
import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    Rental create(LocalDate returnDate, Long carId, Long userId);

    List<Rental> getByUserIdAndStatus(Long id, boolean isActive);

    Rental getById(Long id);

    Rental setActualReturnDate(LocalDate actualReturnDate, Rental rental);
}