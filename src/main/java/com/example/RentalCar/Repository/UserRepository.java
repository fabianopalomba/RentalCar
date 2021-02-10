package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getById(Long id);
    Boolean existsByUsername(String username);
    User getByUsername(String username);
}
