package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.BikePart;
import com.example.bikegarage.model.BikeType;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


import java.util.List;
import java.util.Set;

public class BikeOutputDto {
    public Long id;
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    public Double totalDistanceDriven;
    @Enumerated(EnumType.STRING)
    public BikeType bikeType;
    public Set<BikePart> bikeParts;
    public List<Ride> rides;
    public User user;
    public Integer numberOfRides;
}
