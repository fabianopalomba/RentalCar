package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.CarType;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Repository.BookingRepository;
import com.example.RentalCar.Repository.CarRepository;
import com.example.RentalCar.Repository.CarTypeRepository;
import com.example.RentalCar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class CustomerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarTypeRepository carTypeRepository;

    @Autowired
    BookingRepository bookingRepository;

    //Authentication
    @PostMapping("/signin/{id}")
    public ResponseEntity<User> login(@PathVariable Long id, @RequestBody String password){
        if(userRepository.existsById(id)){
            User user = userRepository.findById(id).get();
            if (user.getPassword().equals(password)){
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

            else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //User
    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<User> getuserbyid(@PathVariable Long id){
        try{
            User user = userRepository.findById(id).get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edituser")
    public ResponseEntity<HttpStatus> edituser(@RequestBody User userfinal){
        if(userRepository.existsById(userfinal.getId())) {
            User userold = userRepository.getOne(userfinal.getId());
            userold.setAdmin(userfinal.getAdmin());
            userold.setBirthday(userfinal.getBirthday());
            userold.setPassword(userfinal.getPassword());
            userold.setName(userfinal.getName());
            userold.setSurname(userfinal.getSurname());
            userRepository.save(userold);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    //Car
    @GetMapping("/getcartypelist")
    public ResponseEntity<List<CarType>> getcartypelist(){
        try {
            List<CarType> carTypes = carTypeRepository.findAll();
            return new ResponseEntity<>(carTypes, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getcarlist")
    public ResponseEntity<List<Car>> getcarlist(){
        try {
            List<Car> cars = carRepository.findAll();
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getcarbytarga/{targa}")
    public ResponseEntity<Car> getcarbytarga(@PathVariable String targa){
        try{
            Car car = carRepository.getByTarga(targa);
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Booking
    @PostMapping("/addbooking")
    public ResponseEntity<HttpStatus> addbooking(@RequestBody Booking booking) throws ParseException {
        LocalDateTime initialdate = LocalDateTime.from(booking.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        LocalDateTime finaldate = LocalDateTime.from(booking.getFinalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        int contatore = 0;
        if (LocalDateTime.now().plusDays(2).isBefore(initialdate) && initialdate.isBefore(finaldate) ){
            List<Booking> bookingList = bookingRepository.getByCar_Targa(booking.getCar().getTarga());
            for (Booking a : bookingList){
                Date indate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getInitialDate().toString());
                Date findate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getFinalDate().toString());
                LocalDateTime initialdatecompared = LocalDateTime.from(indate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                LocalDateTime finaldatecompared = LocalDateTime.from(findate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                if ((initialdate.isAfter(initialdatecompared)&&initialdate.isBefore(finaldatecompared))||(finaldate.isAfter(initialdatecompared)&&finaldate.isBefore(finaldatecompared))){
                    contatore++;
                }
            }
            if (contatore==0){
                bookingRepository.save(booking);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getbookinglistbycar/{targa}")
    public ResponseEntity<List<Booking>> getbookinglistbycar(@PathVariable String targa){
        try {
            List<Booking> bookings = bookingRepository.getByCar_Targa(targa);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbookinglistbyuser/{id}")
    public ResponseEntity<List<Booking>> getbookinglistbyuser(@PathVariable Long id){
        try {
            List<Booking> bookings = bookingRepository.getByUser_Id(id);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editbooking")
    public ResponseEntity<HttpStatus> editbooking(@RequestBody Booking bookingnew) throws ParseException {
        if (bookingRepository.existsById(bookingnew.getId())){
            Booking bookingold = bookingRepository.findById(bookingnew.getId()).get();
            Date oldindate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingold.getInitialDate().toString());
            LocalDateTime initialdateold = LocalDateTime.from(oldindate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            LocalDateTime initialdatenew = LocalDateTime.from(bookingnew.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            LocalDateTime finaldatenew = LocalDateTime.from(bookingnew.getFinalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if (LocalDateTime.now().plusDays(2L).isBefore(initialdateold) && LocalDateTime.now().plusDays(2L).isBefore(initialdatenew) ){
                int contatore = 0;
                List<Booking> bookingList = bookingRepository.getByCar_Targa(bookingnew.getCar().getTarga());
                for (Booking a : bookingList){
                    Date indate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getInitialDate().toString());
                    Date findate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getFinalDate().toString());
                    LocalDateTime initialdatecompared = LocalDateTime.from(indate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    LocalDateTime finaldatecompared = LocalDateTime.from(findate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    if ((initialdatenew.isAfter(initialdatecompared)&&initialdatenew.isBefore(finaldatecompared))||(finaldatenew.isAfter(initialdatecompared)&&finaldatenew.isBefore(finaldatecompared))){
                        contatore++;
                    }
                }
                if (contatore==0){
                    bookingold.setCar(bookingnew.getCar());
                    bookingold.setFinalDate(bookingnew.getFinalDate());
                    bookingold.setInitialDate(bookingnew.getInitialDate());
                    bookingRepository.save(bookingold);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deletebookingbyid/{id}")
    public ResponseEntity<HttpStatus> deletebookingbyid(@PathVariable Long id){
        try{
            bookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
