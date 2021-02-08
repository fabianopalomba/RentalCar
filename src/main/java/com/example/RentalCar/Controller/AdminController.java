package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Service.BookingService;
import com.example.RentalCar.Service.CarService;
import com.example.RentalCar.Service.CarTypeService;
import com.example.RentalCar.Service.UserService;
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
    UserService userService;

    @Autowired
    CarService carService;

    @Autowired
    CarTypeService carTypeService;

    @Autowired
    BookingService bookingService;

    //User
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody User user){
        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getuserlist")
    public ResponseEntity<List<User>> getuserlist(){
        try {
            List<User> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteuserbyid/{id}")
    public ResponseEntity<HttpStatus> deleteuserbyid(@PathVariable Long id){
        try{
            userService.deleteById(id);
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
            carService.save(car);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    //Booking
    @GetMapping("/getbookinglist")
    public ResponseEntity<List<Booking>> getbookinglist(){
        try {
            List<Booking> bookings = bookingService.findAll();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/approvebooking")
    public ResponseEntity<HttpStatus> approvebooking(@RequestBody Booking bookingnew){
        try{
            Booking booking = bookingService.approveBooking(bookingnew);
            if (booking!=null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
