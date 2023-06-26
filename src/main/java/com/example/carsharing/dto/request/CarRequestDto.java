package com.example.carsharing.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {
    private String model;
    private String brand;
    private Integer inventory;
    private String type;
    private BigDecimal dailyFee;
}
