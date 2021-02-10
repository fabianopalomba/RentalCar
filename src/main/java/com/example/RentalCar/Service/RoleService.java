package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.RoleName;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role findByName(RoleName name);
}
