package com.example.bikegarage.dto.input;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.PartType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class PartInputDto {

    @Enumerated(EnumType.STRING)
    public PartType partType;
    public Double maxDistance;
    public LocalDateTime installationDate;
}
