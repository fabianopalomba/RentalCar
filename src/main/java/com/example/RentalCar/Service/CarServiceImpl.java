package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Boolean existsByTarga(String targa) {
        return carRepository.existsByTarga(targa);
    }

    @Override
    public Car getByTarga(String targa) {
        return carRepository.getByTarga(targa);
    }

    @Override
    public void deleteByTarga(String targa) {
        carRepository.deleteByTarga(targa);
    }

    @Override
    public Car updateCar(Car carnew) {
        if (carRepository.existsByTarga(carnew.getTarga())) {
            Car car = carRepository.getByTarga(carnew.getTarga());
            car.setBrand(carnew.getBrand());
            car.setCarType(carnew.getCarType());
            car.setModel(carnew.getModel());
            car.setYear(carnew.getYear());
            carRepository.save(car);
            return car;
        }
        else return null;
    }
}
