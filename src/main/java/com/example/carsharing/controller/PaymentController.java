package com.example.carsharing.controller;

import com.example.carsharing.dto.request.PaymentRequestDto;
import com.example.carsharing.dto.response.PaymentResponseDto;
import com.example.carsharing.model.Payment;
import com.example.carsharing.model.Rental;
import com.example.carsharing.service.PaymentService;
import com.example.carsharing.service.RentalService;
import com.example.carsharing.service.mapper.PaymentMapper;
import com.example.carsharing.service.mapper.StripeService;
import io.swagger.v3.oas.annotations.Operation;
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
    private static final float fineMultiplier = 1.1f;

    private final PaymentService service;
    private final PaymentMapper mapper;
    private final RentalService rentalService;
    private final StripeService stripeService;

    @PostMapping
    @Operation(summary = "initiate payment")
    public PaymentResponseDto create(@RequestBody PaymentRequestDto dto) {
        Payment payment = mapper.toModel(dto);
        Rental rental = rentalService.getById(payment.getRental().getId());
        payment.setRental(rental);
        payment.setType(rental.getActualReturnDate().isAfter(rental.getReturnDate())
                ? Payment.PaymentType.FINE :
                Payment.PaymentType.PAYMENT);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment = service.save(payment);
        long numberOfDays = Math.min(1, Period.between(rental.getRentalDate(),
                rental.getActualReturnDate()).getDays());
        long totalPrice =
                (long) (rental.getCar().getDailyFee().longValue()
                        * (numberOfDays < 1 ? 1 : numberOfDays)
                        * (payment.getType() == Payment.PaymentType.PAYMENT ? 1 : fineMultiplier));
        payment.setStripePaymentUrl(stripeService.getPaymentSessionUrl(payment, totalPrice));
        service.update(payment.getId(), payment);
        return mapper.toResponseDto(payment);
    }

    @GetMapping("/success/{id}")
    @Operation(summary = "will be called on payment success")
    public String paymentSuccess(@PathVariable Long id) {
        Payment toComplete = service.getById(id);
        toComplete.setStatus(Payment.PaymentStatus.PAID);
        service.save(toComplete);
        return "Payment Successful!";
    }

    @GetMapping("/cancel/{id}")
    @Operation(summary = "will be called on payment cancellation")
    public String paymentCancel(@PathVariable Long id) {
        stripeService.deleteStripeProduct(service.getById(id));
        return "Payment Canceled!";
    }
}
