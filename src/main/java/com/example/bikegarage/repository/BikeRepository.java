package com.example.bikegarage.repository;

import com.example.bikegarage.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    Bike findBikeByFrameNumberOrId(Long frameNumber, Long id);

}