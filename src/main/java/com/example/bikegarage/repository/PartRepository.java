package com.example.bikegarage.repository;

import com.example.bikegarage.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
