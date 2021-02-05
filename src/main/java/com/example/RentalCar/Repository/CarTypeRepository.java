package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeRepository extends JpaRepository<CarType, Long> {
}
