package com.example.carsharing.service.impl;

import com.example.carsharing.model.Rental;
import com.example.carsharing.repository.RentalRepository;
import com.example.carsharing.service.RentalService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final InfoBotMessageSender botMessageSender;

    @Override
    public Rental save(Rental rental) {
        rental.setRentalDate(LocalDate.now());
        botMessageSender.sendMassageToUserAboutCreateRental(rental);
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

    @Override
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendMessageToUserEveryDay() {
        List<Rental> all = rentalRepository.findAll();
        all.forEach(botMessageSender::sendMassageToUserAboutRental);
    }
}
