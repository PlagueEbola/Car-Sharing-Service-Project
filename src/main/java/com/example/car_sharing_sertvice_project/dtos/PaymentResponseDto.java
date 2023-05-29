package com.example.car_sharing_sertvice_project.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Integer id;
    private String status;
    private String type;
    private Long rentalId;
    private BigDecimal rentalCost;
}
