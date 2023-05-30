package com.example.car_sharing_sertvice_project.service;

import com.example.car_sharing_sertvice_project.model.User;

public interface UserService {
    User getById(Integer id);

    User save(User user);

    void deleteById(Integer id);
}
