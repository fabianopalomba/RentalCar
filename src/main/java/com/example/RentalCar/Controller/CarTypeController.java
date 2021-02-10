package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.CarType;
import com.example.RentalCar.Service.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class CarTypeController {

    @Autowired
    CarTypeService carTypeService;

    //Customer
    @GetMapping("/getcartypelist")
    public ResponseEntity<List<CarType>> getcartypelist(){
        try {
            List<CarType> carTypes = carTypeService.findAll();
            return new ResponseEntity<>(carTypes, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
