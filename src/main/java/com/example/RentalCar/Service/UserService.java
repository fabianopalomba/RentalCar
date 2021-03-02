package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Security.Jwt.JwtResponse;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    User save(User user);
    List<User> getuserlist();
    @Transactional
    void deleteById(Long id);
    User getById(Long id);
    Boolean existsById(Long id);
    User editUser(User user);
    JwtResponse login(String username, String password);
    User getByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean makeAdmin(String username);
    String validateToken(String token);
}
