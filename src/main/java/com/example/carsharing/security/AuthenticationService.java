package com.example.carsharing.security;

import com.example.carsharing.exception.AuthenticationException;
import com.example.carsharing.model.User;

public interface AuthenticationService {
    User register(String email, String password, String firstName, String lastName);

    User login(String email, String password) throws AuthenticationException;
}
