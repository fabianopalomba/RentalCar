package com.example.RentalCar.Repository;

import com.example.RentalCar.Entity.Booking;
import com.example.RentalCar.Entity.Car;
import com.example.RentalCar.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getByUser_Id(Long id);
    List<Booking> getByCar_TargaAndApprovedIsTrue(String targa);
    List<Booking> getByCar_TargaAndAndIdIsNot(String targa, Long id);
    Booking getById(Long id);
    List<Booking> getAllByIdIsNot(Long id);
}
