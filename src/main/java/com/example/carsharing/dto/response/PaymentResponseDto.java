package com.example.carsharing.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private String status;
    private String type;
    private Long rentalId;
    private String stripePaymentUrl;
}
