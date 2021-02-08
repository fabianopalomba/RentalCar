package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.CarType;
import com.example.RentalCar.Repository.CarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarTypeServiceImpl implements CarTypeService{

    @Autowired
    CarTypeRepository carTypeRepository;


    @Override
    public List<CarType> findAll() {
        return carTypeRepository.findAll();
    }
}
