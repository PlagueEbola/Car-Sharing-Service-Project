package com.example.carsharing.controller;

import com.example.carsharing.dto.request.PaymentRequestDto;
import com.example.carsharing.dto.response.PaymentResponseDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.model.Payment;
import com.example.carsharing.model.Rental;
import com.example.carsharing.service.PaymentService;
import com.example.carsharing.service.RentalService;
import com.example.carsharing.service.mapper.PaymentMapper;
import com.example.carsharing.service.mapper.StripeCarProductService;
import java.time.Period;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
@Component
public class PaymentController {
    private final PaymentService service;
    private final PaymentMapper mapper;
    private final StripeCarProductService stripeService;
    private final RentalService rentalService;

    @PostMapping
    public PaymentResponseDto create(@RequestBody PaymentRequestDto dto) {
        Payment payment = mapper.toModel(dto);
        Rental rental = rentalService.getById(payment.getRental().getId());
        payment.setType(rental.getActualReturnDate().isAfter(rental.getReturnDate())
                ? Payment.PaymentType.FINE :
                Payment.PaymentType.PAYMENT);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment = service.save(payment);
        Car car = rental.getCar();
        payment.setStripePaymentUrl(
                stripeService.getPaymentUrl(
                        car.getStripePriceId(),
                        (long) Period.between(rental.getRentalDate(),
                                rental.getActualReturnDate()).getDays(),
                        payment.getId()));
        return mapper.toResponseDto(service.save(payment));
    }

    @GetMapping("/success/{id}")
    public String paymentSuccess(@PathVariable Long id) {
        Payment toComplete = service.getById(id);
        toComplete.setStatus(Payment.PaymentStatus.PAID);
        service.save(toComplete);
        return "Payment Successful!";
    }
}
