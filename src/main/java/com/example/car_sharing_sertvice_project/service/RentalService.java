package com.example.car_sharing_sertvice_project.service;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    Object create(LocalDate returnDate, Integer carId, Integer userId);

    List<Object> getByUserIdAndStatus(Integer id, boolean isActive);

    Object getById(Integer id);

    Object setActualReturnDate(LocalDate actualReturnDate, Object rental);
}
