package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Repository.BookingRepository;
import com.example.RentalCar.Repository.CarRepository;
import com.example.RentalCar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return bookingRepository.existsById(id);
    }

    @Override
    public Booking getById(Long id) { return bookingRepository.getById(id); }

    @Override
    public Booking save(Booking booking) throws ParseException {
        LocalDateTime initialdate = LocalDateTime.from(booking.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        LocalDateTime finaldate = LocalDateTime.from(booking.getFinalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        int contatore = 0;
        if (LocalDateTime.now().plusDays(2).isBefore(initialdate) && initialdate.isBefore(finaldate)) {
            List<Booking> bookingList = bookingRepository.getByCar_TargaAndApprovedIsTrue(booking.getCar().getTarga());
            for (Booking a : bookingList) {
                Date indate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getInitialDate().toString());
                Date findate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getFinalDate().toString());
                LocalDateTime initialdatecompared = LocalDateTime.from(indate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                LocalDateTime finaldatecompared = LocalDateTime.from(findate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                if ((initialdate.isAfter(initialdatecompared) && initialdate.isBefore(finaldatecompared)) || (finaldate.isAfter(initialdatecompared) && finaldate.isBefore(finaldatecompared))) {
                    contatore++;
                }
            }
            if (contatore == 0) {
                bookingRepository.save(booking);
                return booking;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<Booking> getByCarTarga(String targa) {
        return bookingRepository.getByCar_TargaAndApprovedIsTrue(targa);
    }

    @Override
    public List<Booking> getByUserId(Long id) {
        return bookingRepository.getByUser_Id(id);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking approveBooking(Booking bookingnew) {
        if (bookingRepository.existsById(bookingnew.getId())) {
            Booking bookingold = bookingRepository.getById(bookingnew.getId());
            bookingold.setApproved(bookingnew.getApproved());
            bookingRepository.save(bookingnew);
            return bookingnew;
        }
        else return null;
    }

    @Override
    public Booking editBooking(Booking bookingnew) throws ParseException {
        if (bookingRepository.existsById(bookingnew.getId())){
            Booking bookingold = bookingRepository.getById(bookingnew.getId());
            Date oldindate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingold.getInitialDate().toString());
            LocalDateTime initialdateold = LocalDateTime.from(oldindate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            LocalDateTime initialdatenew = LocalDateTime.from(bookingnew.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            LocalDateTime finaldatenew = LocalDateTime.from(bookingnew.getFinalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if (LocalDateTime.now().plusDays(2L).isBefore(initialdateold) && LocalDateTime.now().plusDays(2L).isBefore(initialdatenew) ){
                int contatore = 0;
                List<Booking> bookingList = bookingRepository.getByCar_TargaAndAndIdIsNot(bookingnew.getCar().getTarga(), bookingold.getId());
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
                    bookingold.setFinalDate(bookingnew.getFinalDate());
                    bookingold.setInitialDate(bookingnew.getInitialDate());
                    bookingold.setApproved(bookingnew.getApproved());
                    bookingRepository.save(bookingold);
                    return bookingold;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<Car> getCarsForEdit(Booking booking) throws ParseException {
        LocalDateTime initialdate = LocalDateTime.from(booking.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        LocalDateTime finaldate = LocalDateTime.from(booking.getFinalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        List<Car> carList = new ArrayList<>();
        if (LocalDateTime.now().plusDays(2).isBefore(initialdate) && initialdate.isBefore(finaldate)) {
            List<Booking> bookingList = bookingRepository.getAllByIdIsNot(booking.getId());
            for (Booking a : bookingList) {
                Date indate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getInitialDate().toString());
                Date findate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getFinalDate().toString());
                LocalDateTime initialdatecompared = LocalDateTime.from(indate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                LocalDateTime finaldatecompared = LocalDateTime.from(findate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                if ((initialdate.isAfter(initialdatecompared) && initialdate.isBefore(finaldatecompared)) || (finaldate.isAfter(initialdatecompared) && finaldate.isBefore(finaldatecompared)) || initialdate.isEqual(initialdatecompared) || finaldate.isEqual(finaldatecompared)) {
                    carList.add(a.getCar());
                }
            }
            if (carList.size() == 0) {
                return carRepository.findAll();
            }
            else {
                List<Car> cars = carRepository.findAll();
                for (Car a : carList){
                    cars.remove(a);
                }
                return cars;
            }
        }
        return null;
    }


    @Override
    public List<Car> getCarsByDates(List<Date> dates) throws ParseException {
        LocalDateTime initialdate = LocalDateTime.from(dates.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        LocalDateTime finaldate = LocalDateTime.from(dates.get(1).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        List<Car> carList = new ArrayList<>();
        if (LocalDateTime.now().plusDays(2).isBefore(initialdate) && initialdate.isBefore(finaldate)) {
            List<Booking> bookingList = bookingRepository.findAll();
            for (Booking a : bookingList) {
                Date indate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getInitialDate().toString());
                Date findate = new SimpleDateFormat("yyyy-MM-dd").parse(a.getFinalDate().toString());
                LocalDateTime initialdatecompared = LocalDateTime.from(indate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                LocalDateTime finaldatecompared = LocalDateTime.from(findate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                if ((initialdate.isAfter(initialdatecompared) && initialdate.isBefore(finaldatecompared)) || (finaldate.isAfter(initialdatecompared) && finaldate.isBefore(finaldatecompared)) || initialdate.isEqual(initialdatecompared) || finaldate.isEqual(finaldatecompared)) {
                    carList.add(a.getCar());
                }
            }
            if (carList.size() == 0) {
                return carRepository.findAll();
            }
            else {
                List<Car> cars = carRepository.findAll();
                for (Car a : carList){
                    cars.remove(a);
                }
                return cars;
            }
        }
        return null;
    }
 }
