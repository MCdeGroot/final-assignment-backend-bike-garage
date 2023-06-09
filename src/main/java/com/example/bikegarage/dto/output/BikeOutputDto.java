package com.example.bikegarage.dto.output;


import com.example.bikegarage.model.Part;
import com.example.bikegarage.model.BikeType;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BikeOutputDto {
    public Long id;
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    public Double totalDistanceDriven;
    public Duration totalHoursDriven;
    @Enumerated(EnumType.STRING)
    public BikeType bikeType;
    public Set<Part> bikeParts;
    public List<Ride> rides;
    public User user;
}
