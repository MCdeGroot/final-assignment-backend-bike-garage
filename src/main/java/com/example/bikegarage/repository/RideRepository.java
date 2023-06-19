package com.example.bikegarage.repository;

import com.example.bikegarage.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

        List<Ride> findByDistanceGreaterThanEqual(Double distance);

        List<Ride> findByDateAfterOrDateEquals(LocalDateTime dateStartTime, LocalDateTime dateEndTime);
        }
