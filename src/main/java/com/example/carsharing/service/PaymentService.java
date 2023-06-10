package com.example.carsharing.service;

import com.example.carsharing.model.Payment;
import java.util.List;

public interface PaymentService {
    Payment getById(Long id);

    List<Payment> getByUserId(Long userId);

    Payment save(Payment payment);

    void deleteById(Long id);

    List<Payment> getAll();
}
