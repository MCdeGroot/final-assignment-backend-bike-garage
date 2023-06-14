package com.example.bikegarage.repository;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
        }
