package com.example.car_sharing_sertvice_project.dtos.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarResponseDto {
    private Integer id;
    private String model;
    private String brand;
    private String type;
    private Integer inventory;
    private BigDecimal dailyFee;
}
