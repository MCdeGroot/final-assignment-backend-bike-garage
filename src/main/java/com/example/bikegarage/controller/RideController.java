package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.RideOutputDto;
import com.example.bikegarage.model.File;
import com.example.bikegarage.service.FileService;
import com.example.bikegarage.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rides")
public class RideController {
    private final FileService fileService;
    private final RideService rideService;

    public RideController(FileService fileService, RideService rideService) {
        this.fileService = fileService;
        this.rideService = rideService;
    }

    @GetMapping
    public ResponseEntity<List<RideOutputDto>> getAllRides(@RequestParam(required = false) Double distance) {
        if (distance != null) {
            return new ResponseEntity<>(rideService.getAllRidesGreaterThanEqual(distance), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rideService.getAllRides(), HttpStatus.OK);
        }
    }
    @GetMapping("/ride-data/{id}")
    public ResponseEntity<RideOutputDto> getRideById(@PathVariable Long id){
        return new ResponseEntity<>(rideService.getRideById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RideOutputDto>> getAllRidesByUsername(@PathVariable String username){
        return new ResponseEntity<>(rideService.getAllRidesByUsername(username), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Object> createRide(@RequestBody RideInputDto rideInputDto, @RequestParam Long bikeId, BindingResult br ){
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        RideOutputDto rideOutputDto = rideService.createRide(rideInputDto, bikeId);

        //hiermee creeer je het pad waarin het object wordt opgeslagen. maar snap niet hoe dit werk!!!!
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + rideOutputDto).toUriString());
        return ResponseEntity.created(uri).body(rideOutputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRide(@PathVariable Long id, @RequestBody RideInputDto newRideInputDto, BindingResult br ){
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        RideOutputDto rideOutputDto = rideService.updateRide(id, newRideInputDto);
        return new ResponseEntity<>(rideOutputDto, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRideById (@PathVariable Long id){
        rideService.deleteRide(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{rideId}/photo")
    public String assignPhotoToRide(@PathVariable Long rideId, @RequestParam("file") MultipartFile file) {
        // next line makes url. example "http://localhost:8080/download-file/id"
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download-file/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        String fileName = fileService.storeFile(file, url);

        File fileUpload = new File(fileName, contentType, url );

        rideService.assignFileToRide(fileUpload.getFileName(), rideId);

        return "upload gelukt";
    }

}
