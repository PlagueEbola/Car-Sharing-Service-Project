package com.example.carsharing.service.impl;

import com.example.carsharing.model.Payment;
import com.example.carsharing.repository.PaymentRepository;
import com.example.carsharing.service.PaymentService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment getById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find car by id " + id));
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void update(Long id, Payment payment) {
        payment.setId(id);
        paymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }
}
