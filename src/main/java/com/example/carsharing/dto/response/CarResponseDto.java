package com.example.carsharing.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {
    private Long id;
    private String model;
    private String brand;
    private String type;
    private Integer inventory;
    private BigDecimal dailyFee;
}
