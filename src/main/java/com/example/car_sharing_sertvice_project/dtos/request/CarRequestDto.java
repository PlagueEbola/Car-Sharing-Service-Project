package com.example.car_sharing_sertvice_project.dtos.request;

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
