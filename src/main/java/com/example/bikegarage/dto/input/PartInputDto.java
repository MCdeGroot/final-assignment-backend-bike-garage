package com.example.bikegarage.dto.input;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.PartType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class PartInputDto {
    @NotNull
    @Enumerated(EnumType.STRING)
    public PartType partType;
    @NotNull
    @Positive
    public Double maxDistance;

    @NotNull
    @PastOrPresent
    public LocalDateTime installationDate;
}
