package com.example.carsharing.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @NotNull
    private Long rentalId;
}
