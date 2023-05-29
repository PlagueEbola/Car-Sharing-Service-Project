package com.example.car_sharing_sertvice_project.service.impl;

import com.example.car_sharing_sertvice_project.model.User;
import com.example.car_sharing_sertvice_project.repository.UserRepository;
import com.example.car_sharing_sertvice_project.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User find(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find user by id " + id));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
