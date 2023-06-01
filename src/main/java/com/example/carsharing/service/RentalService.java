package com.example.carsharing.service;

import com.example.carsharing.model.Rental;
import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    Rental save(Rental rental);

    void update(Long id, Rental rental);

    List<Rental> getByUserIdAndStatus(Long id, boolean isActive);

    Rental getById(Long id);

    Rental setActualReturnDate(LocalDate actualReturnDate, Rental rental);
}
