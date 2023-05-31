package com.example.carsharing.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    private String type;
    private Integer inventory;
    private BigDecimal dailyFee;
}
