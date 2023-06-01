package com.example.carsharing.service;

import com.example.carsharing.model.User;
import java.util.Optional;

public interface UserService {

    User add(User user);

    void update(Long id, User user);

    void deleteById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
