package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.BikeInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.exception.UsernameNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;


@Service
public class BikeService {
    //repository injection
    private final BikeRepository bikeRepository;
    private final UserRepository userRepository;

    public BikeService(BikeRepository bikeRepository, UserRepository userRepository) {
        this.bikeRepository = bikeRepository;
        this.userRepository = userRepository;
    }

    public BikeOutputDto getBikeById(Long id) throws RecordNotFoundException {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + id + " cannot be found"));
        return transferBikeModelToBikeOutputDto(bike);
    }

    public List<BikeOutputDto> getAllBikes() throws RecordNotFoundException {
        List<BikeOutputDto> allBikesOutputDtos = new ArrayList<>();
        List<Bike> bikes = bikeRepository.findAll();
        if (bikes.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any bikes in your possession");
        }
        for (Bike bike : bikes
        ) {
            allBikesOutputDtos.add(transferBikeModelToBikeOutputDto(bike));
        }
        return allBikesOutputDtos;
    }

    public List<BikeOutputDto> getAllBikesByUsername(String username) throws RecordNotFoundException {
        List<BikeOutputDto> allBikesOutputDtos = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        List<Bike> bikes = bikeRepository.findAllByUser(user);
        if (bikes.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any bikes in your possession");
        }
        for (Bike bike : bikes
        ) {
            allBikesOutputDtos.add(transferBikeModelToBikeOutputDto(bike));
        }
        return allBikesOutputDtos;
    }

    public BikeOutputDto createBike(BikeInputDto bikeInputDto, String username) {
        Bike bike = transferBikeInputDtoToBike(bikeInputDto);
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        bike.setUser(user);
        bikeRepository.save(bike);
        return transferBikeModelToBikeOutputDto(bike);
    }

    public BikeOutputDto updateBike(Long id, BikeInputDto bikeInputDto) throws RecordNotFoundException {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + id + " cannot be found"));
        Bike bikeUpdate = updateBikeInputDtoToBike(bikeInputDto, bike);
        bikeRepository.save(bikeUpdate);
        return transferBikeModelToBikeOutputDto(bikeUpdate);
    }

    public String deleteBike(Long id) throws RecordNotFoundException {
        Bike bike = bikeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Bike with id-number" + id + " cannot be found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (!isAdmin && !bike.getUser().getUsername().equals(loggedInUsername)) {
            throw new ForbiddenException("You are not authorized to delete this bike.");
        }
        bike.getRides().forEach(ride -> ride.setBike(null));
        bikeRepository.deleteById(id);
        return "Well well I hope you know what you're doing, because you just removed " + bike.getName() + "!";
    }

    public BikeOutputDto transferBikeModelToBikeOutputDto(Bike bike) {
        BikeOutputDto bikeOutputDto = new BikeOutputDto();
        bikeOutputDto.id = bike.getId();
        bikeOutputDto.frameNumber = bike.getFrameNumber();
        bikeOutputDto.brand = bike.getBrand();
        bikeOutputDto.model = bike.getModel();
        bikeOutputDto.name = bike.getName();
        bikeOutputDto.groupSet = bike.getGroupSet();
        bikeOutputDto.rides = bike.getRides();
        bikeOutputDto.totalDistanceDriven = getTotalDistanceDriven(bike);
        bikeOutputDto.totalHoursDriven = getTotalHoursDriven(bike);
        bikeOutputDto.bikeType = bike.getBikeType();
        bikeOutputDto.bikeParts = bike.getBikeParts();
        bikeOutputDto.user = bike.getUser();

        return bikeOutputDto;
    }

    public Bike transferBikeInputDtoToBike(BikeInputDto bikeInputDto) {
        Bike bike = new Bike();
        bike.setFrameNumber(bikeInputDto.frameNumber);
        bike.setBrand(bikeInputDto.brand);
        bike.setModel(bikeInputDto.model);
        bike.setName(bikeInputDto.name);
        bike.setGroupSet(bikeInputDto.groupSet);
        bike.setBikeType(bikeInputDto.bikeType);

        return bike;
    }

    public Bike updateBikeInputDtoToBike(BikeInputDto bikeInputDto, Bike bike) {
        if (bikeInputDto.frameNumber != null) {
            bike.setFrameNumber(bikeInputDto.frameNumber);
        }
        if (bikeInputDto.brand != null) {
            bike.setBrand(bikeInputDto.brand);
        }
        if (bikeInputDto.model != null) {
            bike.setModel(bikeInputDto.model);
        }
        if (bikeInputDto.name != null) {
            bike.setName(bikeInputDto.name);
        }
        if (bikeInputDto.bikeType != null) {
            bike.setBikeType(bikeInputDto.bikeType);
        }

        return bike;
    }

    public Double getTotalDistanceDriven(Bike bike) {
        Double totalDistanceDriven = 0.0;
        Set<Ride> rides = bike.getRides();
        if (rides != null) {
            for (Ride ride : rides
            ) {
                totalDistanceDriven += ride.getDistance();
            }
        }
        return totalDistanceDriven;
    }

    public Duration getTotalHoursDriven(Bike bike) {
        Duration totalHoursDriven = Duration.ZERO;
        Set<Ride> rides = bike.getRides();
        if (rides != null) {
            for (Ride ride : rides
            ) {
                totalHoursDriven = totalHoursDriven.plus(ride.getTimeRide());
            }
        }
        return totalHoursDriven;
    }

}


//Uitzoekwerk:
//- Hoe krijg ik mijn arraylist van rides een waarde eruit zodat dit kan weergegeven worden in mijn app? is dit uberhaubt wel nodig? Want wellicht kan ik ook gewoon de arraylist returnen en dan via mij frontend met een GET-request de size van de array weergeven op mijn pagina?
//- de Totaldistancedriven, dit is een som van mijn rides. Waar moet ik dit soort dingen schrijven om hier een methode voor te maken die dit berekent?
//- op een of andere manier moet ik per se een List ipv Arraylist defineiren voor mij bikeparts en rides. Morgen Aan PAUL vragen/

