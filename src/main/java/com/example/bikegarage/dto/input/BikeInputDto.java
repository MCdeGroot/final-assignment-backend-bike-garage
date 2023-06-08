package com.example.bikegarage.dto.input;


import com.example.bikegarage.model.BikeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class BikeInputDto {
    public Long frameNumber;
    public String brand;
    public String model;
    public String name;
    @Enumerated(EnumType.STRING)
    public BikeType biketype;
    public Long totalDistanceDriven;
}
