package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.BikeInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.repository.BikeRepository;
import org.springframework.stereotype.Service;


@Service
public class BikeService {
//repository injection
    private final BikeRepository bikeRepository;
    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public BikeOutputDto getBikeById(Long id) throws RecordNotFoundException {
        Bike bike = bikeRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("The bike with "));
        return transferBikeModelToBikeOutputDto(bike);
    }

    public BikeOutputDto createBike(BikeInputDto bikeInputDto){
        Bike bike = transferBikeInputDtoToBike(bikeInputDto);
        bikeRepository.save(bike);
        return transferBikeModelToBikeOutputDto(bike);
    }


    //op een of andere manier kan ik hier geen orElseThrow methode toevoegen.
//    public BikeOutputDto getBikeByFrameNumberOrId(Long frameNumber, Long Id) throws RecordNotFoundException {
//        Bike bike = bikeRepository.findBikeByFrameNumberOrId(frameNumber, Id);
//        return transferBikeModelToBikeOutputDto(bike);
//    }


    public BikeOutputDto transferBikeModelToBikeOutputDto(Bike bike){
        BikeOutputDto bikeOutputDto = new BikeOutputDto();
        bikeOutputDto.id=bike.getId();
        bikeOutputDto.frameNumber = bike.getFrameNumber();
        bikeOutputDto.brand = bike.getBrand();
        bikeOutputDto.model = bike.getModel();
        bikeOutputDto.name = bike.getName();
        bikeOutputDto.totalDistanceDriven = bike.getTotalDistanceDriven();
        bikeOutputDto.bikeType = bike.getBikeType();
        bikeOutputDto.bikeParts = bike.getBikeParts();
        bikeOutputDto.rides = bike.getRides();
        bikeOutputDto.user = bike.getUser();
        //bikeOutputDto.numberOfRides = bike.getRides().size(); // is dit een goede methode? of beter via frontend?

        return bikeOutputDto;
    }

    public Bike transferBikeInputDtoToBike (BikeInputDto bikeInputDto){
        Bike bike = new Bike();
        bike.setFrameNumber(bikeInputDto.frameNumber);
        bike.setBrand(bikeInputDto.brand);
        bike.setModel(bikeInputDto.model);
        bike.setName(bikeInputDto.name);
        bike.setTotalDistanceDriven(bikeInputDto.totalDistanceDriven);// vraag me af of ik dit wel moet doen. Dit moet in principe berekend worden.
        bike.setBikeType(bikeInputDto.biketype);
        bike.setUser(bikeInputDto.user);

        return bike;
    }
}


//Uitzoekwerk:
//- Hoe krijg ik mijn arraylist van rides een waarde eruit zodat dit kan weergegeven worden in mijn app? is dit uberhaubt wel nodig? Want wellicht kan ik ook gewoon de arraylist returnen en dan via mij frontend met een GET-request de size van de array weergeven op mijn pagina?
//- de Totaldistancedriven, dit is een som van mijn rides. Waar moet ik dit soort dingen schrijven om hier een methode voor te maken die dit berekent?
//- op een of andere manier moet ik per se een List ipv Arraylist defineiren voor mij bikeparts en rides. Morgen Aan PAUL vragen/

