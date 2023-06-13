package com.example.bikegarage.controller;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.repository.BikeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeRepository bikeRepository;
    public BikeController(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }
    @GetMapping
    public ResponseEntity<Iterable<Bike>> getAllBikes(){
        return ResponseEntity.ok(bikeRepository.findAll());
    }
//    @PostMapping
//    public ResponseEntity<Bike> addBike(@RequestBody Bike bike){
//        bikeRepository.save(bike);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + bike.getFrameNumber()).toUriString());
//        return ResponseEntity.created(uri).body(bike);
//    }


}
