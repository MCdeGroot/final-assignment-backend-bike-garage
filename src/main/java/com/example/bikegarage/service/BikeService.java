package com.example.bikegarage.service;

import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.repository.BikeRepository;
import org.springframework.stereotype.Service;

@Service
public class BikeService {
//repository injection
    private final BikeRepository bikeRepository;
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

//    public BikeOutputDto getBikeById (Long id){
//
//    }
}
