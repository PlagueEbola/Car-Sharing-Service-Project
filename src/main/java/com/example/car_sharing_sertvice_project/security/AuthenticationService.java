package com.example.car_sharing_sertvice_project.security;

import com.example.car_sharing_sertvice_project.exception.AuthenticationException;
import com.example.car_sharing_sertvice_project.model.User;

public interface AuthenticationService {
    User register(String email, String password, String firstName, String lastName);

    User login(String email, String password) throws AuthenticationException;
}
