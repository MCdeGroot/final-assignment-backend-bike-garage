package com.example.bikegarage.repository;

import com.example.bikegarage.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
