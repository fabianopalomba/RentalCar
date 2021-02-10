package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
