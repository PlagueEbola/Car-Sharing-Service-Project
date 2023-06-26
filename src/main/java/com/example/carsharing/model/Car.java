package com.example.carsharing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import javax.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
@EqualsAndHashCode
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String brand;
    @Enumerated(value = EnumType.STRING)
    private CarType type;
    @Positive
    private Integer inventory;
    @Positive
    private BigDecimal dailyFee;

    public Car() {
    }

    public Car(
            Long id,
            String model,
            String brand,
            CarType type,
            Integer inventory,
            BigDecimal dailyFee
    ) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.inventory = inventory;
        this.dailyFee = dailyFee;
    }

    public Car(String model, String brand, CarType type, Integer inventory, BigDecimal dailyFee) {
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.inventory = inventory;
        this.dailyFee = dailyFee;
    }

    public enum CarType {
        SEDAN,
        SUV,
        HATCHBACK,
        UNIVERSAL
    }
}
