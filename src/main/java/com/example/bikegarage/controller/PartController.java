package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.PartInputDto;
import com.example.bikegarage.dto.output.PartOutputDto;
import com.example.bikegarage.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/bikeparts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PartOutputDto>

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
}
