package com.example.carsharing.service.impl;

import com.example.carsharing.model.Rental;
import com.example.carsharing.repository.RentalRepository;
import com.example.carsharing.service.CarService;
import com.example.carsharing.service.RentalService;
import com.example.carsharing.service.UserService;
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
    public Rental create(LocalDate returnDate, Long carId, Long userId) {
        Rental rental = new Rental();
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(returnDate);
        rental.setCar(carService.getById(carId));
        rental.setUser(userService.findById(userId).orElseThrow(
                () -> new NoSuchElementException("No user found by id: " + userId)));
        return rentalRepository.save(rental);
    }

    @Override
    public List<Rental> getByUserIdAndStatus(Long id, boolean isActive) {
        if (isActive) {
            return rentalRepository.findAllByUserIdAndActualReturnDateIsNotNull(id);
        }
        return rentalRepository.findAllByUserIdAndActualReturnDateIsNull(id);
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find rental by id " + id));
    }

    @Override
    public Rental setActualReturnDate(LocalDate actualReturnDate, Rental rental) {
        rental.setActualReturnDate(actualReturnDate);
        return rentalRepository.save(rental);
    }
}
