package com.example.car_sharing_sertvice_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RentalRepository extends JpaRepository<Object, Integer> {
    List<Object> findAllByActualReturnDateIsNull();
    List<Object> findAllByActualReturnDateIsNotNull();
}
