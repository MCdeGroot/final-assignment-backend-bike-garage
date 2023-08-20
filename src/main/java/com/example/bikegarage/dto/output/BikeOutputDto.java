package com.example.bikegarage.dto.output;


import com.example.bikegarage.model.Part;
import com.example.bikegarage.model.BikeType;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Set;

@Getter
@Setter
public class BikeOutputDto {
    public Long id;
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    public String groupSet;
    public Double totalDistanceDriven;
    public Duration totalHoursDriven;
    @Enumerated(EnumType.STRING)
    public BikeType bikeType;
    public Set<Part> bikeParts;
    public Set<Ride> rides;
    public User user;
}
