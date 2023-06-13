package com.example.bikegarage.dto.input;


import com.example.bikegarage.model.BikePart;
import com.example.bikegarage.model.BikeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Set;

public class BikeInputDto {
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    public Long totalDistanceDriven;
    @Enumerated(EnumType.STRING)
    public BikeType biketype;
    public Set<BikePart> bikeParts;


}
