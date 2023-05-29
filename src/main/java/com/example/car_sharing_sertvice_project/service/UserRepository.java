package com.example.car_sharing_sertvice_project.service;

public interface UserRepository {
    Object find(Long id);

    void create(Object user);

    void update(Long id, Object user);

    void delete(Long id);
}
