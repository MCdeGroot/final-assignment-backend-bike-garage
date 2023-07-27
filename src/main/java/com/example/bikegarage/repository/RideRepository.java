package com.example.bikegarage.repository;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface RideRepository extends JpaRepository<Ride, Long> {

        List<Ride> findByDistanceGreaterThanEqual(Double distance);
        List<Ride> findByBikeAndDateAfterOrDateEquals(Bike bike, LocalDateTime dateStartTime, LocalDateTime dateEndTime);
        List<Ride> findAllByUser(User user);
        }
