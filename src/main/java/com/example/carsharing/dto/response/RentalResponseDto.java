package com.example.carsharing.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalResponseDto {
    private Long id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;
    private Long carId;
    private Long userId;
}
