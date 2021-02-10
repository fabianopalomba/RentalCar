package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.RoleName;
import com.example.RentalCar.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
