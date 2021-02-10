package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    //Admin
    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    //Customer
    @PostMapping("/addbooking")
    public ResponseEntity<HttpStatus> addbooking(@RequestBody Booking booking) {
        try{
            Booking booking1 = bookingService.save(booking);
            if (booking1!=null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<HttpStatus> editbooking(@RequestBody Booking bookingnew) {
        try{
            Booking booking = bookingService.editBooking(bookingnew);
            if (booking != null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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
