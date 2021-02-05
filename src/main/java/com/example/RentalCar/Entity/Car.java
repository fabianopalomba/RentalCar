package com.example.RentalCar.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Car {
    @Id
    @NotBlank
    @Size(min = 7,max = 7)
    private String targa;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private Long year;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CarType.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotBlank
    private CarType carType;

    public Car() {
    }

    public Car(@NotBlank String targa, @NotBlank String brand, @NotBlank String model, @NotBlank Long year, @NotBlank CarType carType) {
        this.targa = targa;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.carType = carType;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
