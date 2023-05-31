package com.example.carsharing.service;

import com.example.carsharing.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    User update(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
