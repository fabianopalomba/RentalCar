package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.CarType;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Service.BookingService;
import com.example.RentalCar.Service.CarService;
import com.example.RentalCar.Service.CarTypeService;
import com.example.RentalCar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class CustomerController {

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @Autowired
    CarTypeService carTypeService;

    @Autowired
    BookingService bookingService;

    //Authentication
    @PostMapping("/signin/{id}")
    public ResponseEntity<User> login(@PathVariable Long id, @RequestBody String password){
        try{
            User user = userService.login(id, password);
            if (user!=null) return new ResponseEntity<>(user, HttpStatus.OK);
            else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //User
    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<User> getuserbyid(@PathVariable Long id){
        try{
            User user = userService.getById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edituser")
    public ResponseEntity<HttpStatus> edituser(@RequestBody User userfinal){
        try{
            User user = userService.editUser(userfinal);
            if (user != null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Car
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

    //Booking
    @PostMapping("/addbooking")
    public ResponseEntity<HttpStatus> addbooking(@RequestBody Booking booking) throws ParseException {
        try{
            Booking booking1 = bookingService.save(booking);
            if (booking1!=null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbookinglistbycar/{targa}")
    public ResponseEntity<List<Booking>> getbookinglistbycar(@PathVariable String targa){
        try {
            List<Booking> bookings = bookingService.getByCarTarga(targa);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbookinglistbyuser/{id}")
    public ResponseEntity<List<Booking>> getbookinglistbyuser(@PathVariable Long id){
        try {
            List<Booking> bookings = bookingService.getByUserId(id);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editbooking")
    public ResponseEntity<HttpStatus> editbooking(@RequestBody Booking bookingnew) throws ParseException {
        try{
            Booking booking = bookingService.editBooking(bookingnew);
            if (booking != null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletebookingbyid/{id}")
    public ResponseEntity<HttpStatus> deletebookingbyid(@PathVariable Long id){
        try{
            bookingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
