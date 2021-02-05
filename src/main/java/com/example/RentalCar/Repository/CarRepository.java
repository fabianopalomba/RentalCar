package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car getByTarga(String targa);
    Boolean existsByTarga(String targa);
    @Transactional
    void deleteByTarga(String targa);
}
