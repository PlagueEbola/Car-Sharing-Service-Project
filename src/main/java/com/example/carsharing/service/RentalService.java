package com.example.carsharing.service;

import com.example.carsharing.model.Rental;
import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    Rental create(LocalDate returnDate, Integer carId, Integer userId);

    List<Rental> getByUserIdAndStatus(Integer id, boolean isActive);

    Rental getById(Integer id);

    Rental setActualReturnDate(LocalDate actualReturnDate, Rental rental);
}
