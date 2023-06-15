package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.PartType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


import java.time.LocalDateTime;

public class PartOutputDto {
    public Long id;
    public String name;
    @Enumerated(EnumType.STRING)
    public PartType partType;
    public Double currentDistanceDriven;
    public Double maxDistance;

    public Bike bike;
    public LocalDateTime installationDate;
}
