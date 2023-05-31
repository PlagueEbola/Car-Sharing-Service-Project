package com.example.carsharing.service;

import com.example.carsharing.model.User;

public interface UserService {
    User getById(Integer id);

    User save(User user);

    void deleteById(Integer id);
}
