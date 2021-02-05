package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
