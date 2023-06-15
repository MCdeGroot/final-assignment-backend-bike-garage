package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.PartInputDto;
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
import java.util.List;

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

    public PartOutputDto createBikePart(PartInputDto partInputDto, Long bikeId) {
        Part part = transferPartInputDtoToPart(partInputDto);
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new RecordNotFoundException("Bike with id-number " + bikeId + " cannot be found"));
        List<Ride> ridesAfterDate = rideRepository.findAllByDateAfterOrDateEquals(part.getInstallationDate(), LocalDateTime.now());
        Double currentDistance = 0.0;
            for (Ride ride : ridesAfterDate){
                currentDistance += ride.getDistance();
        }
        part.setBike(bike);
            part.setCurrentDistanceDriven(currentDistance);
        partRepository.save(part);
        return transferPartModelToPartOutputDto(part);
    }

   // List<Ride> rides = rideRepository.findAllByDateAfterOrDateEquals(part.getInstallationDate());
    public PartOutputDto transferPartModelToPartOutputDto(Part part) {
        PartOutputDto partOutputDto = new PartOutputDto();
        partOutputDto.id = part.getId();
        partOutputDto.name = part.getName();
        partOutputDto.partType = part.getPartType();
        partOutputDto.currentDistanceDriven = part.getCurrentDistanceDriven();
        partOutputDto.maxDistance = part.getMaxDistance();
        partOutputDto.bike = part.getBike();
        partOutputDto.installationDate = part.getInstallationDate();

        return partOutputDto;
    }

    public Part transferPartInputDtoToPart(PartInputDto partInputDto) {
        Part part = new Part();
        part.setName(partInputDto.name);
        part.setPartType(partInputDto.partType);
        part.setMaxDistance(partInputDto.maxDistance);
        part.setInstallationDate(partInputDto.installationDate);

        return part;
    }

    //Methods


}



