package com.example.bikegarage.service;


import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.RideOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.exception.UsernameNotFoundException;
import com.example.bikegarage.model.*;
import com.example.bikegarage.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final BikeRepository bikeRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;


    public RideService(RideRepository rideRepository, BikeRepository bikeRepository, PartRepository partRepository, UserRepository userRepository, FileRepository fileRepository) {
        this.rideRepository = rideRepository;
        this.bikeRepository = bikeRepository;
        this.partRepository = partRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public RideOutputDto getRideById(Long id) throws RecordNotFoundException {
        Ride ride = rideRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Ride with id-number " + id + " cannot be found"));
        return transferRideModelToRideOutputDto(ride);
    }

    public List<RideOutputDto> getAllRides() throws RecordNotFoundException {
        List<RideOutputDto> allRidesOutputDtos = new ArrayList<>();
        List<Ride> rides = rideRepository.findAll();
        if (rides.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any rides ridden yet");
        }
        for (Ride ride : rides
        ) {
            allRidesOutputDtos.add(transferRideModelToRideOutputDto(ride));
        }
        return allRidesOutputDtos;
    }

    public List<RideOutputDto> getAllRidesByUsername(String username) throws RecordNotFoundException {
        Set<Ride> uniqueRides = new HashSet<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        List<User> cyclists = userRepository.findCyclistsByTrainer(user);

        if (cyclists != null) {
            for (User cyclist : cyclists) {
                if (cyclist.getRides() != null) {
                    uniqueRides.addAll(cyclist.getRides());
                }
            }
        }

        List<Ride> userRides = rideRepository.findAllByUser(user);
        uniqueRides.addAll(userRides);

        List<RideOutputDto> allRidesOutputDtos = new ArrayList<>();
        for (Ride ride : uniqueRides) {
            allRidesOutputDtos.add(transferRideModelToRideOutputDto(ride));
        }
        return allRidesOutputDtos;
    }

    public List<RideOutputDto> getAllRidesGreaterThanEqual(Double distance) throws RecordNotFoundException {
        List<RideOutputDto> allRidesOutputDtos = new ArrayList<>();
        List<Ride> rides = rideRepository.findByDistanceGreaterThanEqual(distance);
        if (rides.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any GranFondo's yet");
        }
        for (Ride ride : rides
        ) {
            allRidesOutputDtos.add(transferRideModelToRideOutputDto(ride));
        }
        return allRidesOutputDtos;
    }


    // Create ride, user always must select a bike that he drove during the ride. Can't create a ride without a bike
    public RideOutputDto createRide(RideInputDto rideInputDto, Long bikeId) {
        Ride ride = transferRideInputDtoToRide(rideInputDto);
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + bikeId + " cannot be found"));
        ride.setBike(bike);
        rideRepository.save(ride);
        updateBikeParts(ride, ride.getDistance());
        return transferRideModelToRideOutputDto(ride);
    }

    public RideOutputDto updateRide(Long id, RideInputDto rideInputDto) throws RecordNotFoundException {
        Ride ride = rideRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Ride with id-number " + id + " cannot be found"));
        Double distanceDifference = rideInputDto.distance - ride.getDistance();
        Ride rideUpdate = updateRideInputDtoToRide(rideInputDto, ride);
        updateBikeParts(ride, distanceDifference);
        return transferRideModelToRideOutputDto(rideUpdate);
    }

    public String deleteRide(Long id) throws RecordNotFoundException {
        Ride ride = rideRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Ride with id-number" + id + " cannot be found"));
        Double distanceDifference = 0.0 - ride.getDistance();
        rideRepository.deleteById(id);
        updateBikeParts(ride, distanceDifference);
        return "Well well I hope you know what you're doing, because you just removed " + ride.getTitleRide() + "!";
    }

    public void assignFileToRide(String name, Long rideId) throws RecordNotFoundException{
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RecordNotFoundException("Ride with id-number" + rideId + " cannot be found"));;
        File file = fileRepository.findByFileName(name).orElseThrow(() -> new RecordNotFoundException("kan de file niet vinden"));

            ride.setFile(file);
            rideRepository.save(ride);
        }




    public RideOutputDto transferRideModelToRideOutputDto(Ride ride) {
        RideOutputDto rideOutputDto = new RideOutputDto();
        rideOutputDto.id = ride.getId();
        rideOutputDto.titleRide = ride.getTitleRide();
        rideOutputDto.subTitleRide = ride.getSubTitleRide();
        rideOutputDto.distance = ride.getDistance();
        rideOutputDto.date = ride.getDate();
        rideOutputDto.averagePower = ride.getAveragePower();
        rideOutputDto.normalizedPower = ride.getNormalizedPower();
        rideOutputDto.timeRide = ride.getTimeRide();
        rideOutputDto.bike = ride.getBike();
        rideOutputDto.user = ride.getUser();
        if (ride.getReview() != null) {
            rideOutputDto.reviewRating = ride.getReview().getRating();
        }
        if (ride.getFile() != null){
        rideOutputDto.url = ride.getFile().getUrl();
        }

        return rideOutputDto;
    }

    public Ride transferRideInputDtoToRide(RideInputDto rideInputDto) {
        Ride ride = new Ride();
        Optional<User> userOptional = userRepository.findByUsername(rideInputDto.username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("There is no user found with username " + rideInputDto.username + " in the database!");
        }
        User user = userOptional.get();
        ride.setTitleRide(rideInputDto.titleRide);
        ride.setSubTitleRide(rideInputDto.subTitleRide);
        ride.setDistance(rideInputDto.distance);
        ride.setDate(rideInputDto.date);
        ride.setAveragePower(rideInputDto.averagePower);
        ride.setNormalizedPower(rideInputDto.normalizedPower);
        ride.setTimeRide(rideInputDto.timeRide);
        ride.setBike(rideInputDto.bike);
        ride.setUser(user);

        return ride;
    }

    public Ride updateRideInputDtoToRide(RideInputDto rideInputDto, Ride ride) {
        if (rideInputDto.titleRide != null) {
            ride.setTitleRide(rideInputDto.titleRide);
        }
        if (rideInputDto.subTitleRide != null) {
            ride.setSubTitleRide(rideInputDto.subTitleRide);
        }
        if (rideInputDto.distance != null) {
            ride.setDistance(rideInputDto.distance);
        }
        if (rideInputDto.date != null) {
            ride.setDate(rideInputDto.date);
        }
        if (rideInputDto.bike != null) {
            ride.setBike(rideInputDto.bike);
        }

        return ride;
    }

    public void updateBikeParts(Ride ride, Double distance) {
        for (Part part : ride.getBike().getBikeParts()
        ) {
            if (part.getInstallationDate().isBefore(ride.getDate())) {
                part.updateCurrentDistanceDriven(distance);
                partRepository.save(part);
            }
        }

    }
}
