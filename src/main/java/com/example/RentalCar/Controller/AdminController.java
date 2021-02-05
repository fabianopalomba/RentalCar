package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Repository.BookingRepository;
import com.example.RentalCar.Repository.CarRepository;
import com.example.RentalCar.Repository.CarTypeRepository;
import com.example.RentalCar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarTypeRepository carTypeRepository;

    @Autowired
    BookingRepository bookingRepository;

    //User
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody User user){
        try {
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getuserlist")
    public ResponseEntity<List<User>> getuserlist(){
        try {
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteuserbyid/{id}")
    public ResponseEntity<HttpStatus> deleteuserbyid(@PathVariable Long id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Car
    @PostMapping("/addcar")
    public ResponseEntity<HttpStatus> addcar(@RequestBody Car car){
        try {
            carRepository.save(car);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatecar")
    public ResponseEntity<HttpStatus> updatecar(@RequestBody Car carnew){
        if(carRepository.existsByTarga(carnew.getTarga())){
            Car car = carRepository.getByTarga(carnew.getTarga());
            car.setBrand(carnew.getBrand());
            car.setCarType(carnew.getCarType());
            car.setModel(carnew.getModel());
            car.setYear(carnew.getYear());
            carRepository.save(car);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deletecarbytarga/{targa}")
    public ResponseEntity<HttpStatus> deletecarbytarga(@PathVariable String targa){
        try{
            carRepository.deleteByTarga(targa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Booking
    @GetMapping("/getbookinglist")
    public ResponseEntity<List<Booking>> getbookinglist(){
        try {
            List<Booking> bookings = bookingRepository.findAll();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/approvebooking")
    public ResponseEntity<HttpStatus> approvebooking(@RequestBody Booking bookingnew){
        if (bookingRepository.existsById(bookingnew.getId())){
            Booking bookingold = bookingRepository.getOne(bookingnew.getId());
            bookingold.setApproved(bookingnew.getApproved());
            bookingRepository.save(bookingnew);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
