package com.example.bikegarage.repository;

import com.example.bikegarage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {
    Optional<File> findByFileName(String fileName);
}
