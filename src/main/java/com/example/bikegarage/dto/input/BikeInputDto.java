package com.example.bikegarage.dto.input;



import com.example.bikegarage.model.BikeType;

import com.example.bikegarage.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;



public class BikeInputDto {
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    @Enumerated(EnumType.STRING)
    public BikeType bikeType;

    // ik vraag mij dus af of dit nodig is voor een Input DTO. Vragen aan PAUL
   // public Set<BikePart> bikeParts;
   // public ArrayList<Ride> rides;
    public User user;
}
