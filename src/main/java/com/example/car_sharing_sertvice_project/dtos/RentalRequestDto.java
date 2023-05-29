package com.example.car_sharing_sertvice_project.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;
    private Integer carId;
    private Integer userId;
}
