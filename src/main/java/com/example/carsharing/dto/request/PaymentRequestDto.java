package com.example.carsharing.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String status;
    private String type;
    private Long rentalId;
    private BigDecimal rentalCost;
}
