package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Booking;

import java.text.ParseException;
import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    boolean existsById(Long id);
    Booking getById(Long id);
    Booking save(Booking booking) throws ParseException;
    List<Booking> getByCarTarga(String targa);
    List<Booking> getByUserId(Long id);
    void deleteById(Long id);
    Booking approveBooking(Booking booking);
    Booking editBooking(Booking booking) throws ParseException;
}
