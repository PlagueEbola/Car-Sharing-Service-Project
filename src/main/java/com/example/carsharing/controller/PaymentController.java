package com.example.carsharing.controller;

import com.example.carsharing.dto.request.PaymentRequestDto;
import com.example.carsharing.dto.response.PaymentResponseDto;
import com.example.carsharing.model.Payment;
import com.example.carsharing.model.Rental;
import com.example.carsharing.service.PaymentService;
import com.example.carsharing.service.RentalService;
import com.example.carsharing.service.mapper.PaymentMapper;
import com.example.carsharing.service.impl.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private static final float FINE_MULTIPLIER = 1.1f;

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
        payment.setPrice(BigDecimal.valueOf(
                (long) ((rental.getCar().getDailyFee().longValue()
                        * (numberOfDays < 1 ? 1 : numberOfDays)
                        * (payment.getType() == Payment.PaymentType.PAYMENT ? 1 : FINE_MULTIPLIER))
                        / 100)
        ));
        payment.setStripePaymentUrl(stripeService.getPaymentSessionUrl(payment,
                payment.getPrice().longValue() * 100));
        payment = service.save(payment);
        return mapper.toResponseDto(payment);
    }

    @GetMapping
    @Operation(summary = "gets user's payment history")
    public List<PaymentResponseDto> getPaymentHistory(@RequestParam("user_id") Long id) {
        return service.getByUserId(id)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
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
