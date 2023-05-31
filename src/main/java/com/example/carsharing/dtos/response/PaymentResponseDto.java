package com.example.carsharing.dtos.response;

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
