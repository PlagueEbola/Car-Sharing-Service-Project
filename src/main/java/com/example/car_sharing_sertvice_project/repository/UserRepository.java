package com.example.car_sharing_sertvice_project.repository;

import com.example.car_sharing_sertvice_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
