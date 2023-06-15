package com.example.bikegarage.repository;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PartRepository extends JpaRepository<Part, Long> {

    List<Part> findAllByBike(Bike bike);
}
