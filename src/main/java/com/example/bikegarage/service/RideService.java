package com.example.bikegarage.service;

import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.RideRepository;

public class RideService {
    private final RideRepository rideRepository;
    private final BikeRepository bikeRepository;

    public RideService(RideRepository rideRepository, BikeRepository bikeRepository) {
        this.rideRepository = rideRepository;
        this.bikeRepository = bikeRepository;
    }

}
