package com.example.car_sharing_sertvice_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car_types")
@Getter
@Setter
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "car_type_name")
    @Enumerated(value = EnumType.STRING)
    private TypeName typeName;

    private enum TypeName {
        SEDAN,
        SUV,
        HATCHBACK,
        UNIVERSAL
    }
}
