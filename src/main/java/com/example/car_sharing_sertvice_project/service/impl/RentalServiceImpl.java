package com.example.car_sharing_sertvice_project.service.impl;

import com.example.car_sharing_sertvice_project.model.Rental;
import com.example.car_sharing_sertvice_project.repository.RentalRepository;
import com.example.car_sharing_sertvice_project.service.CarService;
import com.example.car_sharing_sertvice_project.service.RentalService;
import com.example.car_sharing_sertvice_project.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final UserService userService;

    @Override
    public Rental create(LocalDate returnDate, Integer carId, Integer userId) {
        Rental rental = new Rental();
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(returnDate);
        rental.setCar(carService.getById(carId));
        rental.setUser(userService.getById(userId));
        return rentalRepository.save(rental);
    }

    @Override
    public List<Rental> getByUserIdAndStatus(Integer id, boolean isActive) {
        if (isActive) {
            return rentalRepository.findAllByUserIdAndActualReturnDateIsNotNull(id);
        }
        return rentalRepository.findAllByUserIdAndActualReturnDateIsNull(id);
    }

    @Override
    public Rental getById(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find rental by id " + id));
    }

    @Override
    public Rental setActualReturnDate(LocalDate actualReturnDate, Rental rental) {
        rental.setActualReturnDate(actualReturnDate);
        return rentalRepository.save(rental);
    }
}
