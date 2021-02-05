package com.example.RentalCar.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String carTypeName;

    public CarType() {
    }

    public CarType(@NotBlank String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}
