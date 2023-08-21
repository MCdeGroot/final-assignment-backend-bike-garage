package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.PartType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartOutputDto {
    public Long id;
    @Enumerated(EnumType.STRING)
    public PartType partType;
    public Double currentDistanceDriven;
    public Double maxDistance;

    public Bike bike;
    public LocalDateTime installationDate;
}
