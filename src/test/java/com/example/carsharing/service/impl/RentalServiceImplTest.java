package com.example.carsharing.service.impl;

import com.example.carsharing.model.Rental;
import com.example.carsharing.repository.RentalRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {
    @InjectMocks
    private RentalServiceImpl rentalService;

    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private InfoBotMessageSender botMessageSender;

    @Test
    void save() {
        Rental rental = new Rental();
        Mockito.doNothing().when(botMessageSender).sendMassageToUserAboutCreateRental(any());
        rentalService.save(rental);
        Assertions.assertEquals(LocalDate.now(), rental.getRentalDate());
    }

    @Test
    void getByUserIdAndStatus() {
        Mockito.when(rentalRepository.findAllByUserIdAndActualReturnDateIsNotNull(1L))
                .thenReturn(List.of(new Rental(), new Rental()));
        Mockito.when(rentalRepository.findAllByUserIdAndActualReturnDateIsNull(1L))
                .thenReturn(List.of(new Rental()));
        List<Rental> actual = rentalService.getByUserIdAndStatus(1L, true);
        Assertions.assertEquals(2, actual.size());
        actual = rentalService.getByUserIdAndStatus(1L, false);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void setActualReturnDate() {
        Rental rental = new Rental();
        LocalDate actualRentalDate = LocalDate.now();
        rentalService.setActualReturnDate(actualRentalDate, rental);
        Assertions.assertEquals(LocalDate.now(), rental.getActualReturnDate());
    }
}
