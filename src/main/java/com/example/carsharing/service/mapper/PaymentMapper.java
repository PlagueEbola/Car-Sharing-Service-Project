package com.example.carsharing.service.mapper;

import com.example.carsharing.dto.request.PaymentRequestDto;
import com.example.carsharing.dto.response.PaymentResponseDto;
import com.example.carsharing.model.Payment;
import com.example.carsharing.model.Rental;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public Payment toModel(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        Rental rental = new Rental();
        rental.setId(requestDto.getRentalId());
        payment.setRental(rental);
        return payment;
    }

    public PaymentResponseDto toResponseDto(Payment payment) {
        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setId(payment.getId());
        responseDto.setType(payment.getType().name());
        responseDto.setStatus(payment.getStatus().name());
        responseDto.setRentalId(payment.getRental().getId());
        responseDto.setStripePaymentUrl(payment.getStripePaymentUrl());
        return responseDto;
    }
}
