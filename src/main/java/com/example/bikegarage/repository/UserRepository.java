package com.example.bikegarage.repository;

import com.example.bikegarage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername (String username);
    List<User> findCyclistsByTrainer(User trainer);
}
