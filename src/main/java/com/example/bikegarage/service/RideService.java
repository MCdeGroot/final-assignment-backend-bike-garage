package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.dto.output.RideOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final BikeRepository bikeRepository;
    public List<RideOutputDto> getAllRides() throws RecordNotFoundException{
        List<RideOutputDto> allRidesOutputDtos = new ArrayList<>();
        List<Ride> rides = rideRepository.findAll();
        if (rides.isEmpty()){
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any rides ridden yet");
        }
        for (Ride ride:rides
        ) {allRidesOutputDtos.add(transferRideModelToRideOutputDto(ride));
        }
        return allRidesOutputDtos;
    }

    public RideService(RideRepository rideRepository, BikeRepository bikeRepository) {
        this.rideRepository = rideRepository;
        this.bikeRepository = bikeRepository;
    }



    public RideOutputDto createRide(RideInputDto rideInputDto, Long bikeId){
        Ride ride = transferRideInputDtoToRide(rideInputDto);
        Bike bike = bikeRepository.findById(bikeId).orElseThrow();
        ride.setBike(bike);
        bike.updateTotalDistanceDriven(bike, ride);

                        /// wellicht hier ook de bikeparts straks toevoegen en dan de deze ook koppelen aan een ride
        rideRepository.save(ride);
        return transferRideModelToRideOutputDto(ride);
    }



    public RideOutputDto transferRideModelToRideOutputDto(Ride ride){
        RideOutputDto rideOutputDto = new RideOutputDto();
        rideOutputDto.titleRide = ride.getTitleRide();
        rideOutputDto.subTitleRide = ride.getSubTitleRide();
        rideOutputDto.distance = ride.getDistance();
        rideOutputDto.date=ride.getDate();
        rideOutputDto.bike=ride.getBike();
        rideOutputDto.user=ride.getUser();

        return rideOutputDto;
    }

    public Ride transferRideInputDtoToRide(RideInputDto rideInputDto){
        Ride ride = new Ride();
        ride.setTitleRide(rideInputDto.titleRide);
        ride.setSubTitleRide(rideInputDto.subTitleRide);
        ride.setDistance(rideInputDto.distance);
        ride.setDate(rideInputDto.date);
        ride.setBike(rideInputDto.bike);
        ride.setUser(rideInputDto.user);

        return ride;
    }

    public Ride updateRideInputDtoToRide(RideInputDto rideInputDto, Ride ride){
        if(rideInputDto.titleRide != null){
            ride.setTitleRide(rideInputDto.titleRide);
        }
        if(rideInputDto.subTitleRide != null){
            ride.setSubTitleRide(rideInputDto.subTitleRide);
        }
        if(rideInputDto.distance != null){
            ride.setDistance(rideInputDto.distance);
        }
        if(rideInputDto.date != null){
            ride.setDate(rideInputDto.date);
        }
        if(rideInputDto.bike != null){
            ride.setBike(rideInputDto.bike);
        }
        if(rideInputDto.user != null){
            ride.setUser(rideInputDto.user);
        }
        return ride;
    }

}
