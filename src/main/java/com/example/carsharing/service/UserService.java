package com.example.carsharing.service;

import com.example.carsharing.model.User;

public interface UserService {
    User getById(Long id);

    User save(User user);

    void update(Long id, User user);

    void deleteById(Long id);
}
