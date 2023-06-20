package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.PartInputDto;
import com.example.bikegarage.dto.output.PartOutputDto;
import com.example.bikegarage.service.PartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/bikeparts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartOutputDto> getPartById(@PathVariable Long id){
        return new ResponseEntity<>(partService.getPartById(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PartOutputDto>> getAllParts(@RequestParam(required = false) Long bikeId) {
        if (bikeId != null) {
            return new ResponseEntity<>(partService.getAllPartsByBike(bikeId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(partService.getAllParts(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createBikePart(@RequestBody PartInputDto partInputDto, @RequestParam Long bikeId, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        PartOutputDto partOutputDto = partService.createBikePart(partInputDto, bikeId);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/"+ partOutputDto).toUriString());
        return ResponseEntity.created(uri).body(partOutputDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBikePart(@PathVariable Long id, @RequestBody PartInputDto partInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        PartOutputDto partOutputDto = partService.updateBikePart(id, partInputDto);
        return new ResponseEntity<>(partOutputDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deletePartById(@PathVariable Long id) {
            partService.deletePart(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


