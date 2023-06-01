package com.example.carsharing.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    private Long carId;
    private Long userId;
}
