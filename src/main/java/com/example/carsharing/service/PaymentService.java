package com.example.carsharing.service;

import com.example.carsharing.model.Payment;
import java.util.List;

public interface PaymentService {
    Payment getById(Long id);

    Payment save(Payment payment);

    void update(Long id, Payment payment);

    void deleteById(Long id);

    List<Payment> getAll();
}
