package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.BikeInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.service.BikeService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Object> createBike(@Valid @RequestBody BikeInputDto bikeInputDto, @RequestParam String username, BindingResult br){
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
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + bikeOutputDto).toUriString());
            return ResponseEntity.created(uri).body(bikeOutputDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBike(@PathVariable Long id, @Valid @RequestBody BikeInputDto newBikeInputDto, BindingResult br ){
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBikeById (@PathVariable Long id){
        bikeService.deleteBike(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
