package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.PartInputDto;
import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.PartOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Part;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.PartRepository;
import com.example.bikegarage.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final RideRepository rideRepository;
    private final BikeRepository bikeRepository;

    public PartService(PartRepository partRepository, RideRepository rideRepository, BikeRepository bikeRepository) {
        this.partRepository = partRepository;
        this.rideRepository = rideRepository;
        this.bikeRepository = bikeRepository;
    }

    public PartOutputDto getPartById(Long id) throws RecordNotFoundException {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Part with id " + id + " cannot be found"));
        return transferPartModelToPartOutputDto(part);
    }

    public List<PartOutputDto> getAllParts() throws RecordNotFoundException {
        List<PartOutputDto> allPartsOutputDtos = new ArrayList<>();
        List<Part> bikeParts = partRepository.findAll();

        if (bikeParts.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any parts.");
        }
        for (Part part : bikeParts) {
            allPartsOutputDtos.add(transferPartModelToPartOutputDto(part));
        }
        return allPartsOutputDtos;
    }

    public List<PartOutputDto> getAllPartsByBike(Long bikeId) throws RecordNotFoundException {
        List<PartOutputDto> allPartsByBikeOutputDtos = new ArrayList<>();
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + bikeId + " cannot be found"));
        Set<Part> bikeParts = bike.getBikeParts();

        if (bikeParts.isEmpty()) {
            throw new RecordNotFoundException("I'm sorry but it looks like you don't have any parts on this bike.");
        }
        for (Part part : bikeParts) {
            allPartsByBikeOutputDtos.add(transferPartModelToPartOutputDto(part));
        }
        return allPartsByBikeOutputDtos;
    }

    public PartOutputDto createBikePart(PartInputDto partInputDto, Long bikeId) {
        Part part = transferPartInputDtoToPart(partInputDto);
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + bikeId + " cannot be found"));
        List<Ride> ridesAfterDate = rideRepository.findByDateAfterOrDateEquals(part.getInstallationDate(), LocalDateTime.now());
        Double currentDistance = 0.0;
        for (Ride ride : ridesAfterDate) {
            currentDistance += ride.getDistance();
        }
        part.setBike(bike);
        part.setCurrentDistanceDriven(currentDistance);
        partRepository.save(part);
        return transferPartModelToPartOutputDto(part);
    }

    public PartOutputDto updateBikePart(Long id, PartInputDto partInputDto) throws RecordNotFoundException {
        Part bikePart = partRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Bike part with id-number " + id + " cannot be found"));
        Part bikePartUpdate = updatePartInputDtoToPart(partInputDto, bikePart);
        List<Ride> ridesAfterDate = rideRepository.findByDateAfterOrDateEquals(bikePartUpdate.getInstallationDate(), LocalDateTime.now());
        Double currentDistance = 0.0;
        for (Ride ride : ridesAfterDate) {
            currentDistance += ride.getDistance();
        }
        bikePartUpdate.setCurrentDistanceDriven(currentDistance);
        partRepository.save(bikePartUpdate);
        return transferPartModelToPartOutputDto(bikePartUpdate);
    }

    public String deletePart(Long id) throws RecordNotFoundException {
        Part part = partRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Part with id-number " + id + " cannot be found"));
        partRepository.deleteById(id);
        return "Well well I hope you know what you're doing, because you just removed " + part.getName() + "!";
    }


    // List<Ride> rides = rideRepository.findAllByDateAfterOrDateEquals(part.getInstallationDate());
    public PartOutputDto transferPartModelToPartOutputDto(Part part) {
        PartOutputDto partOutputDto = new PartOutputDto();
        partOutputDto.id = part.getId();
        partOutputDto.partType = part.getPartType();
        partOutputDto.currentDistanceDriven = part.getCurrentDistanceDriven();
        partOutputDto.maxDistance = part.getMaxDistance();
        partOutputDto.bike = part.getBike();
        partOutputDto.installationDate = part.getInstallationDate();

        return partOutputDto;
    }

    public Part transferPartInputDtoToPart(PartInputDto partInputDto) {
        Part part = new Part();
        part.setPartType(partInputDto.partType);
        part.setMaxDistance(partInputDto.maxDistance);
        part.setInstallationDate(partInputDto.installationDate);

        return part;
    }

    //Methods

    public Part updatePartInputDtoToPart(PartInputDto partInputDto, Part part) {
        if (partInputDto.partType != null) {
            part.setPartType(partInputDto.partType);
        }
        if (partInputDto.maxDistance != null) {
            part.setMaxDistance(partInputDto.maxDistance);
        }
        if (partInputDto.installationDate != null) {
            part.setInstallationDate(partInputDto.installationDate);
        }
        return part;
    }


}



