package com.example.car_sharing_sertvice_project.service;

import com.example.car_sharing_sertvice_project.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    User update(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
