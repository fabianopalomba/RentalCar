package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class CarController {

    @Autowired
    CarService carService;

    //Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addcar")
    public ResponseEntity<HttpStatus> addcar(@RequestBody Car car){
        try {
            carService.save(car);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatecar")
    public ResponseEntity<HttpStatus> updatecar(@RequestBody Car carnew){
        try{
            Car car = carService.updateCar(carnew);
            if (car!=null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletecarbytarga/{targa}")
    public ResponseEntity<HttpStatus> deletecarbytarga(@PathVariable String targa){
        try{
            carService.deleteByTarga(targa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Customer
    @GetMapping("/getcarlist")
    public ResponseEntity<List<Car>> getcarlist(){
        try {
            List<Car> cars = carService.findAll();
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getcarbytarga/{targa}")
    public ResponseEntity<Car> getcarbytarga(@PathVariable String targa){
        try{
            Car car = carService.getByTarga(targa);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
