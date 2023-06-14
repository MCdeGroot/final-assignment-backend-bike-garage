package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.dto.output.RideOutputDto;
import com.example.bikegarage.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideService rideService;


    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    public ResponseEntity<Object> createRide(@RequestBody RideInputDto rideInputDto, @RequestParam Long bikeId ){
        RideOutputDto rideOutputDto = rideService.createRide(rideInputDto, bikeId);

        //hiermee creeer je het pad waarin het object wordt opgeslagen. maar snap niet hoe dit werk!!!!
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + rideOutputDto).toUriString());
        return ResponseEntity.created(uri).body(rideOutputDto);
    }
}
