package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car getByTarga(String targa);
    Boolean existsByTarga(String targa);
    @Transactional
    void deleteByTarga(String targa);
}
