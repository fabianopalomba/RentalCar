package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);
    Boolean existsByUsername(String username);
    User getByUsername(String username);
    List<User> findAllByRoles(Role role);
}
