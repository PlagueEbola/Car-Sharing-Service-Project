package com.example.car_sharing_sertvice_project.service;

public interface CarService {
    Object find(Long id);

    void create(Object car);

    void update(Long id, Object car);

    void delete(Long id);
}
