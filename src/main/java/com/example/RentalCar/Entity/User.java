package com.example.RentalCar.Entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthday;

    @NotNull
    private Boolean admin;

    public User() {
    }

    public User(@NotBlank @Size(min = 3, max = 50) String name, @NotBlank @Size(min = 3, max = 50) String surname, @NotBlank @Size(min = 6, max = 100) String password, @NotNull Date birthday, @NotNull Boolean admin) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthday = birthday;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() { return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getAdmin() { return admin; }

    public void setAdmin(Boolean admin) { this.admin = admin; }
}

