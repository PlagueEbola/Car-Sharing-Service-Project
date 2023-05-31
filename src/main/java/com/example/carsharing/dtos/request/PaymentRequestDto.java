package com.example.carsharing.dtos.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String status;
    private String type;
    private Integer rentalId;
    private BigDecimal rentalCost;
}
