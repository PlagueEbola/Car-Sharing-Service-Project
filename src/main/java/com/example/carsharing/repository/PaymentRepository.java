package com.example.carsharing.repository;

import com.example.carsharing.model.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByRentalUserId(Long id);
}
