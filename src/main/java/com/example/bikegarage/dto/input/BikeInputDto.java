package com.example.bikegarage.dto.input;



import com.example.bikegarage.model.BikeType;

import com.example.bikegarage.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class BikeInputDto {
    public Long frameNumber;
    @NotNull
    @Size(max = 25, message = "Brand name can only be 25 characters long.")
    public String brand;
    @Size(max = 50, message = "Model name can only be 50 characters long.")
    public String model;
    @Size(max = 50, message = "The nickname of your bike can only be 50 characters long.")
    public String name;
    @Size(max = 50, message = "Group set name can only be 50 characters long.")
    public String groupSet;
    @NotNull
    @Enumerated(EnumType.STRING)
    public BikeType bikeType;

    // ik vraag mij dus af of dit nodig is voor een Input DTO. Vragen aan PAUL
   // public Set<BikePart> bikeParts;
   // public ArrayList<Ride> rides;

}
