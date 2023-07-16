package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.BikeInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.service.BikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService bikeService;
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    //het liefst wil ik hem met id of framenumber; moet ik hier aparte mappings voor maken of kan dit in één?
    //Of moeten we dit in de frontend regelen?
    @GetMapping("/bike-data/{id}")
    public ResponseEntity<BikeOutputDto> getBikeById(@PathVariable Long id){
        return new ResponseEntity<>(bikeService.getBikeById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BikeOutputDto>> getAllBikes(){
        return new ResponseEntity<>(bikeService.getAllBikes(),HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<BikeOutputDto>> getAllBikesByUsername(@PathVariable String username){
        return new ResponseEntity<>(bikeService.getAllBikesByUsername(username),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createBike(@RequestBody BikeInputDto bikeInputDto, @RequestParam String username, BindingResult br){
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            BikeOutputDto bikeOutputDto = bikeService.createBike(bikeInputDto, username);
            //hiermee creeer je het pad waarin het object wordt opgeslagen. maar snap niet hoe dit werkt!!!!
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + bikeOutputDto).toUriString());
            return ResponseEntity.created(uri).body(bikeOutputDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBike(@PathVariable Long id, @RequestBody BikeInputDto newBikeInputDto, BindingResult br ){
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        BikeOutputDto bikeOutputDto = bikeService.updateBike(id, newBikeInputDto);
        return new ResponseEntity<>(bikeOutputDto, HttpStatus.ACCEPTED);
    }

    //TODO aan paul vragen hoe dit werkt. Ik kan nu mijn bike niet verwijderen als hier onderdelen op zitten. Ook niet als er al ritten op zitten. Dit moet dus aangepast worden, want ik wil wel bikes kunnen verwijderen en dan mogen gelijk alle onderdelen verwijderd worden, maar ik wil niet dat de ritten worden veriwjderd als de bike veriwjderd wordt.
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBikeById (@PathVariable Long id){
        bikeService.deleteBike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @GetMapping
//    public ResponseEntity<Iterable<Bike>> getAllBikes(){
//        return ResponseEntity.ok(bikeRepository.findAll());
//    }
//    @PostMapping
//    public ResponseEntity<Bike> addBike(@RequestBody Bike bike){
//        bikeRepository.save(bike);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + bike.getFrameNumber()).toUriString());
//        return ResponseEntity.created(uri).body(bike);
//    }


}
