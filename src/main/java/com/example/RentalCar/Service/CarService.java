package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Car;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface CarService {
    List<Car> findAll();
    Car save(Car car);
    Boolean existsByTarga(String targa);
    Car getByTarga(String targa);
    @Transactional
    void deleteByTarga(String targa);
    Car updateCar(Car car);
}
