package com.example.car_sharing_sertvice_project.dtos.mapper;

import com.example.car_sharing_sertvice_project.dtos.request.PaymentRequestDto;
import com.example.car_sharing_sertvice_project.dtos.response.PaymentResponseDto;
import com.example.car_sharing_sertvice_project.model.Payment;
import com.example.car_sharing_sertvice_project.model.Rental;

public class PaymentMapper {
    public Payment toModel(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        Rental rental = new Rental();
        rental.setId(requestDto.getRentalId());
        payment.setRental(rental);
        payment.setRentalCost(requestDto.getRentalCost());
        payment.setType(Payment.PaymentType.valueOf(requestDto.getType()));
        payment.setStatus(Payment.PaymentStatus.valueOf(requestDto.getStatus()));
        return payment;
    }

    public PaymentResponseDto toResponseDto(Payment payment) {
        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setId(payment.getId());
        responseDto.setType(payment.getType().name());
        responseDto.setStatus(payment.getStatus().name());
        responseDto.setRentalId(Long.valueOf(payment.getRental().getId()));
        responseDto.setRentalCost(payment.getRentalCost());
        return responseDto;
    }
}
