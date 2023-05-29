package com.example.car_sharing_sertvice_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@EqualsAndHashCode
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    @OneToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
    @PositiveOrZero
    private BigDecimal rentalCost;

    private enum PaymentStatus {
        PENDING,
        PAID
    }

    private enum PaymentType {
        PAYMENT,
        FINE
    }
}

