package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserOutputDto {
    public String username;
    public String firstName;
    public String lastName;
    public Character gender;
    public LocalDate dateOfBirth;
    public Double totalDistanceDriven;
    public ArrayList<Bike> bikes;

    public ArrayList<Ride> rides;
    public String photoUrl;
}
