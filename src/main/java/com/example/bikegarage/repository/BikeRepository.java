package com.example.bikegarage.repository;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    Bike findBikeByFrameNumberOrId(Long frameNumber, Long id);

    List<Bike> findAllByUser(User user);

}