package com.example.car_sharing_sertvice_project.dtos.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String status;
    private String type;
    private Integer rentalId;
    private BigDecimal rentalCost;
}
