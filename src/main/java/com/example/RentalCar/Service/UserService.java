package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
    void deleteById(Long id);
    User getById(Long id);
    Boolean existsById(Long id);
    User editUser(User user);
    User login(Long id, String password);
}
